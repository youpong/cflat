package cflat.compiler;

import cflat.ast.AST;
import cflat.ast.AbstractAssignNode;
import cflat.ast.AddressNode;
import cflat.ast.ArefNode;
import cflat.ast.AssignNode;
import cflat.ast.BlockNode;
import cflat.ast.CastNode;
import cflat.ast.DereferenceNode;
import cflat.ast.ExprNode;
import cflat.ast.FuncallNode;
import cflat.ast.LHSNode;
import cflat.ast.Location;
import cflat.ast.MemberNode;
import cflat.ast.OpAssignNode;
import cflat.ast.PrefixOpNode;
import cflat.ast.PtrMemberNode;
import cflat.ast.StmtNode;
import cflat.ast.SuffixOpNode;
import cflat.ast.VariableNode;
import cflat.entity.DefinedFunction;
import cflat.entity.DefinedVariable;
import cflat.exception.SemanticError;
import cflat.exception.SemanticException;
import cflat.type.CompositeType;
import cflat.type.Type;
import cflat.type.TypeTable;
import cflat.utils.ErrorHandler;

/**
 * 式の妥当性のチェック
 */
class DereferenceChecker extends Visitor {
    private final TypeTable typeTable;
    private final ErrorHandler errorHandler;

    public DereferenceChecker(TypeTable types, ErrorHandler errorHandler) {
        this.typeTable = types;
        this.errorHandler = errorHandler;
    }

    public void check(AST ast) throws SemanticException {
        for (DefinedVariable var : ast.definedVariables()) {
            checkToplevelVariable(var);
        }
        for (DefinedFunction f : ast.definedFunctions()) {
            check(f.body());
        }
        if (errorHandler.errorOccured()) {
            throw new SemanticException("compile failed.");
        }
    }

    // TODO: test
    private void checkToplevelVariable(DefinedVariable var) {
        checkVariable(var);
        if (var.hasInitializer()) {
            checkConstant(var.initializer());
        }
    }

    private void checkConstant(ExprNode expr) {
        if (!expr.isConstant()) {
            errorHandler.error(expr.location(), "not a constant");
        }
    }

    private void check(StmtNode node) {
        node.accept(this);
    }

    private void check(ExprNode node) {
        node.accept(this);
    }

    //
    // Statements
    //

    // TODO: implement
    public Void visit(BlockNode node) {
        for (DefinedVariable var : node.variables()) {
            checkVariable(var);
        }
        for (StmtNode stmt : node.stmts()) {
            try {
                check(stmt);
            } catch (SemanticError error) {
                ;
            }
        }
        return null;
    }

    private void checkVariable(DefinedVariable var) {
        if (var.hasInitializer()) {
            try {
                check(var.initializer());
            } catch (SemanticError err) {
                ;
            }
        }
    }

    //
    // Assignment Expressions
    //

    public Void visit(AssignNode node) {
        super.visit(node);
        checkAssignment(node);
        return null;
    }

    public Void visit(OpAssignNode node) {
        super.visit(node);
        checkAssignment(node);
        return null;
    }

    private void checkAssignment(AbstractAssignNode node) {
        if (!node.lhs().isAssignable()) {
            semanticError(node.location(), "invalid lhs expression");
        }
    }

    //
    // Expressions
    //

    public Void visit(PrefixOpNode node) {
        super.visit(node);
        if (!node.expr().isAssignable()) {
            semanticError(node.expr().location(), "cannot increment/decrement");
        }
        return null;
    }

    public Void visit(SuffixOpNode node) {
        super.visit(node);
        if (!node.expr().isAssignable()) {
            semanticError(node.expr().location(), "cannot increment/decrement");
        }
        return null;
    }

    public Void visit(FuncallNode node) {
        super.visit(node);
        if (!node.expr().isCallable()) {
            semanticError(node.location(), "calling object is not a function");
        }
        return null;
    }

    public Void visit(ArefNode node) {
        super.visit(node);
        if (!node.expr().isPointer()) {
            semanticError(node.location(), "indexing non-array/pointer expression");
        }
        handleImplicitAddress(node);
        return null;
    }

    //
    public Void visit(MemberNode node) {
        super.visit(node);
        checkMemberRef(node.location(), node.expr().type(), node.member());
        handleImplicitAddress(node);
        return null;
    }

    public Void visit(PtrMemberNode node) {
        super.visit(node);
        if (!node.expr().isPointer()) {
            undereferableError(node.location());
        }
        checkMemberRef(node.location(), node.dereferedType(), node.member());
        handleImplicitAddress(node);
        return null;
    }

    private void checkMemberRef(Location loc, Type t, String memb) {
        if (!t.isCompositeType()) {
            semanticError(loc,
                    "accessing member '" + memb + "' for non-struct/union: " + t);
        }
        CompositeType type = t.getCompositeType();
        if (!type.hasMember(memb)) {
            semanticError(loc, type.toString() + " does not have member: " + memb);
        }
    }

    public Void visit(DereferenceNode node) {
        super.visit(node);
        if (!node.expr().isPointer()) {
            undereferableError(node.location());
        }
        handleImplicitAddress(node);
        return null;
    }

    public Void visit(AddressNode node) {
        super.visit(node);
        if (!node.expr().isLvalue()) {
            semanticError(node.location(), "invalid expression for &");
        }
        Type base = node.expr().type();
        if (!node.expr().isLoadable()) {
            node.setType(base);
        } else {
            node.setType(typeTable.pointerTo(base));
        }
        return null;
    }

    public Void visit(VariableNode node) {
        super.visit(node);
        if (node.entity().isConstant()) {
            checkConstant(node.entity().value());
        }
        handleImplicitAddress(node);
        return null;
    }

    public Void visit(CastNode node) {
        super.visit(node);
        if (node.type().isArray()) {
            semanticError(node.location(), "cast specifies array type");
        }
        return null;
    }

    //
    // Utilities
    //

    private void handleImplicitAddress(LHSNode node) {
        if (!node.isLoadable()) {
            Type t = node.type();
            if (t.isArray()) {
                node.setType(typeTable.pointerTo(t.baseType()));
            } else {
                node.setType(typeTable.pointerTo(t));
            }
        }
    }

    private void undereferableError(Location loc) {
        semanticError(loc, "dereferencing non-pointer expression");
    }

    /*
    private void semanticError(Node n, String msg) {
        semanticError(n.location(), msg);
    }
    */
    private void semanticError(Location loc, String msg) {
        errorHandler.error(loc, msg);
        throw new SemanticError("invalid expr");
    }
}
