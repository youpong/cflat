package cflat.type;

import cflat.ast.Location;
import cflat.ast.TypeNode;

public class UserType extends NamedType {
    protected TypeNode real;
    
    public UserType(String name, TypeNode real, Location loc) {
	super(name, loc);
	this.real = real;
    }

    public Type realType() {
	return real.type();
    }
    //
    
    //
    // Forward methods to real type.
    //

    public long size() { return realType().size(); }
    //
    public boolean isSameType(Type other) {
	return realType().isSameType(other);
    }
    public boolean isCompatible(Type other) {
	return realType().isCompatible(other);
    }
    public boolean isCastableTo(Type other) {
	return realType().isCastableTo(other);
    }
    //
}
