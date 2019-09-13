package cflat.compiler;

import cflat.ast.AST;
import cflat.ast.BinaryOpNode;
import cflat.ast.BlockNode;
import cflat.ast.CastNode;
import cflat.ast.IntegerLiteralNode;
import cflat.ast.Node;
import cflat.ast.Location;
import cflat.ast.StmtNode;
import cflat.ast.ExprNode;
import cflat.entity.DefinedFunction;
import cflat.entity.DefinedVariable;
import cflat.entity.Parameter;
import cflat.exception.SemanticException;
import cflat.type.TypeTable;
import cflat.type.Type;
import cflat.type.IntegerType;
import cflat.utils.ErrorHandler;

// TODO: implement
/**
 * 静的型チェック
 */
public class TypeChecker extends Visitor
{
    private TypeTable typeTable;
    private ErrorHandler errorHandler;

    // TODO: test
    public TypeChecker(TypeTable typeTable, ErrorHandler errorHandler) {
	this.typeTable = typeTable;
	this.errorHandler = errorHandler;
    }

    private void check(StmtNode node) {
	visitStmt(node);
    }
    private void check(ExprNode node) {
	visitExpr(node);
    }
    
    DefinedFunction currentFunction;
    
    // TODO: implement    
    public void check(AST ast) throws SemanticException {
	for (DefinedVariable var : ast.definedVariables()) {
	    checkVariable(var);
	}
	for (DefinedFunction f : ast.definedFunctions()) {
	    currentFunction = f;
	    checkReturnType(f);
	    checkParamTypes(f);
	    check(f.body());
	}
	if (errorHandler.errorOccured()) {
	    throw new SemanticException("compile failed");
	}
    }
    //
    private ExprNode implicitCast(Type targetType, ExprNode expr) {
	if (expr.type().isSameType(targetType)) {
	    return expr;
	} else if (expr.type().isCastableTo(targetType)) {
	    if (! expr.type().isCompatible(targetType)
		&& ! isSafeIntegerCast(expr, targetType)) {
		warn(expr, "incompatible implicit cast from " +
		     expr.type() + " to " + targetType);
	    }
	    return new CastNode(targetType, expr);
	} else {
	    invalidCastError(expr, expr.type(), targetType);
	    return expr;
	}
    }
    private Type integralPromotion(Type t) {
	if (!t.isInteger()) {
	    throw new Error("integralPromotion for " + t);
	}
	Type intType = typeTable.signedInt();
	if (t.size() < intType.size()) {
	    return intType;
	} else {
	    return t;
	}
    }
    //
    private boolean isSafeIntegerCast(Node node, Type type) {
	if (! type.isInteger()) return false;
	IntegerType t = (IntegerType)type;
	if (! (node instanceof IntegerLiteralNode)) return false;
	IntegerLiteralNode n = (IntegerLiteralNode)node;
	return t.isInDomain(n.value());
    }
    //
    private void checkReturnType(DefinedFunction f) {
	if (isInvalidReturnType(f.returnType())) {
	    error(f.location(), "return invalid type: " + f.returnType());
	}
    }
    private void checkParamTypes(DefinedFunction f) {
	for (Parameter param : f.parameters()) {
	    if (isInvalidParameterType(param.type())) {
		error(param.location(), "invalid parameter type: " +
		      param.type());
	    }
	}
    }

    //
    // Statements
    //

    // TODO: implement
    public Void visit(BlockNode node) {
	return null;
    }
    public void checkVariable(DefinedVariable var) {
	if (isInvalidVariableType(var.type())) {
	    error(var.location(), "invalid variable type");
	    return;
	}
	if (var.hasInitializer()) {
	    if (isInvalidLHSType(var.type())) {
		error(var.location(), "invalid LHS type: " + var.type());
		return;
	    }
	    check(var.initializer());
	    var.setInitializer(implicitCast(var.type(), var.initializer()));
	}
    }
    public Void visit(BinaryOpNode node) {
	super.visit(node);
	if (node.operator().equals("+") || node.operator().equals("-")) {
	    expectsSameIntegerOrPointerDiff(node);
	} else if (node.operator().equals("*")
		   || node.operator().equals("/")
		   || node.operator().equals("%")
		   || node.operator().equals("&")
		   || node.operator().equals("|")
		   || node.operator().equals("^")
		   || node.operator().equals("<<")
		   || node.operator().equals(">>")) {
	    expectsSameInteger(node);
	} else if (node.operator().equals("==")
		   || node.operator().equals("!=")
		   || node.operator().equals("<")
		   || node.operator().equals("<=")
		   || node.operator().equals(">")
		   || node.operator().equals(">=")) {
	    expectsComparableScalars(node);
	} else {
	    throw new Error("unknown binary operator: " + node.operator());
	}
	return null;
    }
    private void expectsSameIntegerOrPointerDiff(BinaryOpNode node) {
	if (node.left().isPointer() && node.right().isPointer()) {
	    if (node.operator().equals("+")) {
		error(node, "invalid operation: operation: pointer + pointer");
		return;
	    }
	    node.setType(typeTable.ptrDiffType());
	} else if (node.left().isPointer()) {
	    mustBeInteger(node.right(), node.operator());
	    node.setRight(integralPromotedExpr(node.right()));
	    node.setType(node.left().type());
	} else if (node.right().isPointer()) {
	    if (node.operator().equals("-")) {
		error(node, "invalid operation: integer - pointer");
		return;
	    }
	    mustBeInteger(node.left(), node.operator());
	    node.setLeft(integralPromotedExpr(node.left()));
	    node.setType(node.right().type());
	} else {
	    expectsSameInteger(node);
	}
    }

    private ExprNode integralPromotedExpr(ExprNode expr) {
	Type t = integralPromotion(expr.type());
	if (t.isSameType(expr.type())) {
	    return expr;
	} else {
	    return new CastNode(t, expr);
	}
    }
    private void expectsSameInteger(BinaryOpNode node) {
	if (! mustBeInteger(node.left(), node.operator())) return;
	if (! mustBeInteger(node.right(), node.operator())) return;
	arithmeticImplicitCast(node);
    }
    private void expectsComparableScalars(BinaryOpNode node) {
	if (! mustBeScalar(node.left(), node.operator())) return;
	if (! mustBeScalar(node.right(), node.operator())) return;
	if (node.left().type().isPointer()) {
	    ExprNode right = forcePointerType(node.left(), node.right());
	    node.setRight(right);
	    node.setType(node.left().type());
	    return;
	}
	if (node.right().type().isPointer()) {
	    ExprNode left = forcePointerType(node.right(), node.left());
	    node.setLeft(left);
	    node.setType(node.right().type());
	    return;
	}
	arithmeticImplicitCast(node);
    }
    private ExprNode forcePointerType(ExprNode master, ExprNode slave) {
	if (master.type().isCompatible(slave.type())) {
	    // needs no cast
	    return slave;
	} else {
	    warn(slave, "incompatible implicit cast from " +
		 slave.type() + " to " + master.type());
	    return new CastNode(master.type(), slave);
	}
    }
	
    private void arithmeticImplicitCast(BinaryOpNode node) {
	Type r = integralPromotion(node.right().type());
	Type l = integralPromotion(node.left().type());
	Type target = usualArithmeticConversion(l, r);
	if (! l.isSameType(target)) {
	    // insert cast on left expr
	    node.setLeft(new CastNode(target, node.left()));
	}
	if (! r.isSameType(target)) {
	    // insert cast on right expr
	    node.setRight(new CastNode(target, node.right()));
	}
	node.setType(target);
    }
    private Type usualArithmeticConversion(Type l, Type r) {
	Type s_int = typeTable.signedInt();
	Type u_int = typeTable.unsignedInt();
	Type s_long = typeTable.signedLong();
	Type u_long = typeTable.unsignedLong();
	if ( (l.isSameType(u_int) && r.isSameType(s_long))
	     || (r.isSameType(u_int) && l.isSameType(s_long))) {
	    return u_long;
	} else if (l.isSameType(u_long) && r.isSameType(u_long)) {
	    return u_long;
	} else if (l.isSameType(s_long) && r.isSameType(s_long)) {
	    return s_long;
	} else if (l.isSameType(u_int) && r.isSameType(u_int)) {
	    return u_int;
	} else {
	    return s_int;
	}
    }

    private boolean isInvalidReturnType(Type t) {
	return t.isStruct() || t.isUnion() || t.isArray();
    }
    private boolean isInvalidParameterType(Type t) {
	return t.isStruct() || t.isUnion() || t.isVoid() ||
	    t.isIncompleteArray();
    }
    private boolean isInvalidVariableType(Type t) {
	return t.isVoid() || (t.isArray() && ! t.isAllocatedArray());
    }
    private boolean isInvalidLHSType(Type t) {
	// Array is OK if it is declared as a type of parameter.
	return t.isStruct() || t.isUnion() || t.isVoid();
    }
    //
    private boolean mustBeInteger(ExprNode expr, String op) {
	if (! expr.type().isInteger()) {
	    wrongTypeError(expr, op);
	    return false;
	}
	return true;
    }
    private boolean mustBeScalar(ExprNode expr, String op) {
	if (! expr.type().isScalar()) {
	    wrongTypeError(expr, op);
	    return false;
	}
	return true;
    }
    private void invalidCastError(Node n, Type l, Type r) {
	error(n, "invalid cast from " + l + " to " + r);
    }
    private void wrongTypeError(ExprNode expr, String op) {
	error(expr, "wrong operand type for " + op + ": " + expr.type());
    }
    private void warn(Node n, String msg) {
	errorHandler.warn(n.location(), msg);
    }
    private void error(Node n, String msg) {
	errorHandler.error(n.location(), msg);
    }
    private void error(Location loc, String msg) {
	errorHandler.error(loc, msg);
    }
}
