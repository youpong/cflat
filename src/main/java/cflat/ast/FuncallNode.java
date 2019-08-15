package cflat.ast;

import cflat.type.Type;
import cflat.exception.SemanticException;
import java.util.*;

/**
 * Function call
 */
public class FuncallNode extends ExprNode {
    protected ExprNode expr;
    protected List<ExprNode> args;
    
    public FuncallNode(ExprNode expr, List<ExprNode> args) {
	this.expr = expr;
	this.args = args;
    }

    // TODO: implement
    public Type type() {
	return null;	
	/*
	try {
	    return functionType().returnType();
	}
	catch(ClassCastException err) {
	    throw new SemanticException(err.getMessage());
	}
	*/
    }
    /*
    public FunctionType functionType() {
	return expr.type().getPointerType().baseType().getFunctionType();
    }
    */
    public ExprNode expr() {
	return expr;
    }
    public List<ExprNode> args() {
	return args;
    }
    public Location location() {
	return expr.location();
    }
    protected void _dump(Dumper d) {
	d.printMember("expr", expr);
	d.printNodeList("args", args);
    }
    public <S,E> E accept(ASTVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
}
