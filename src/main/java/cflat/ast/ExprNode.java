package cflat.ast;

import cflat.type.Type;
import cflat.exception.SemanticError;

abstract public class ExprNode extends Node {
    public ExprNode() {
	super();
    }
    abstract public Type type();
    protected Type origType() { return type(); }
    //
    public boolean isLvalue() { return false; }
    //
    public boolean isLoadable() { return false; }    
    //
    public boolean isPointer() {
	try {
	    return type().isPointer();
	} catch (SemanticError err) {
	    return false;
	}
    }
    abstract public <S,E> E accept(ASTVisitor<S,E> visitor);
}
