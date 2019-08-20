package cflat.type;

import cflat.ast.Location;

public class VoidTypeRef extends TypeRef {
    public VoidTypeRef() {
	super(null);
    }
    public VoidTypeRef(Location loc) {
	super(loc);
    }
    public String toString() {
	return "void";
    }
}
