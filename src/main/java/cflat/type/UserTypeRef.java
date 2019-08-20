package cflat.type;

import cflat.ast.Location;

public class UserTypeRef extends TypeRef {
    protected String name;

    public UserTypeRef(String name) {
	this(null, name);
    }
    public UserTypeRef(Location loc, String name) {
	super(loc);
	this.name = name;
    }
    public String name() {
	return name;
    }
    public String toString() {
	return name;
    }
}
