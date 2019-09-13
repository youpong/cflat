package cflat.entity;

import cflat.ast.Dumpable;
import cflat.ast.Dumper;
import cflat.ast.TypeNode;
import cflat.ast.Location;
import cflat.ast.ExprNode;
import cflat.type.Type;

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
    public String name() {
	return name;
    }
    //    public String symbolString()
    abstract public boolean isDefined();
    //    abstract public boolean isInitialized();
    
    public boolean isConstant() { return false; }

    public ExprNode value() {
	throw new Error("Entity#value");
    }
    // isParameter()
    public boolean isPrivate() {
	return isPrivate;
    }
    //
    public void refered() {
	nRefered++;
    }
    public boolean isRefered() {
	return (nRefered > 0);
    }
    //
    public TypeNode typeNode() {
	return typeNode;
    }
    public Type type() {
	return typeNode.type();
    }
    public long allocSize() {
	return type().allocSize();
    }
    //
    public Location location() {
	return typeNode.location();
    }

    abstract public <T> T accept(EntityVisitor<T> visitor);
    
    public void dump(Dumper d) {
	d.printClass(this, location());
	_dump(d);
    }
    
    abstract protected void _dump(Dumper d);
}
