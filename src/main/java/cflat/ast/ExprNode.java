package cflat.ast;

import cflat.type.Type;

abstract public class ExprNode extends Node {
    public ExprNode() {
	super();
    }
    abstract public Type type();
    protected Type origType() { return type(); }

    abstract public <S,E> E accept(ASTVisitor<S,E> visitor);
}
