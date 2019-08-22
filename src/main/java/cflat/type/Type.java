package cflat.type;

import cflat.exception.SemanticError;

public abstract class Type {
    
    public boolean isVoid() { return false; }

    public boolean isPointer() { return false; }
    public boolean isArray() { return false; }
    
    public Type baseType() {
	throw new SemanticError("#baseType called for undereferable type");
    }
}
