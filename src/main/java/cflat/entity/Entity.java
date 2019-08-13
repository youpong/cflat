package cflat.entity;

import cflat.ast.Dumpable;
import cflat.ast.Dumper;
import cflat.ast.TypeNode;
import cflat.ast.Location;

abstract public class Entity implements Dumpable {
    protected String name;
    protected TypeNode typeNode;
    protected boolean isPrivate;
    protected long nRefered;
    
    public Entity(boolean priv, TypeNode type, String name) {
	this.isPrivate = priv;
	this.typeNode = type;
	this.name = name;
	this.nRefered = 0;
    }

    public TypeNode typeNode() {
	return typeNode;
    }
    public Location location() {
	return typeNode.location();
    }
    public void dump(Dumper d) {
	d.printClass(this, location());
	_dump(d);
    }
    
    abstract protected void _dump(Dumper d);
}
