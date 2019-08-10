package cflat.type;

import cflat.exception.SemanticError;

public abstract class Type {
    public Type baseType() {
	throw new SemanticError("#baseType called for undereferable type");
    }
}
