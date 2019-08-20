package cflat.type;

import cflat.ast.Location;

abstract public class NamedType extends Type {
    protected String name;
    protected Location loc;

    public NamedType(String name, Location loc) {
	this.name = name;
	this.loc = loc;
    }

    public Location location() {
	return loc;
    }
}
