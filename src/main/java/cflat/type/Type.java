package cflat.type;

import cflat.exception.SemanticError;

public abstract class Type {
    
    public boolean isVoid() { return false; }

    public boolean isPointer() { return false; }
    public boolean isArray() { return false; }
    public boolean isCompositeType() { return false; }
    //
    public boolean isCallable() { return false; }
    //
    
    public Type baseType() {
	throw new SemanticError("#baseType called for undereferable type");
    }

    // Cast methods
    public PointerType getPointerType() { return (PointerType)this; }
    //
    public CompositeType getCompositeType() { return (CompositeType)this; }
    //
}
