package cflat.ast;

import cflat.type.Type;

abstract public class LHSNode extends ExprNode {
    protected Type type, origType;
    
    public Type type() {
	return type != null ? type : origType();
    }

    abstract protected Type origType();
    
}
