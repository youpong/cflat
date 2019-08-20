package cflat.type;

import cflat.ast.Location;

public class UnionTypeRef extends TypeRef {
    protected String name;
    
    public UnionTypeRef(String name) {
	this(null, name);
    }
    public UnionTypeRef(Location loc, String name) {
	super(loc);
	this.name = name;
    }
    public String toString() {
	return "union " + name;
    }
}
