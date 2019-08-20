package cflat.ast;

import cflat.type.Type;

public class Slot extends Node {
    protected TypeNode typeNode;
    protected String name;
    
    public Slot(TypeNode t, String n) {
	this.typeNode = t;
	this.name = n;
    }
    public TypeNode typeNode() {
	return typeNode;
    }
    public Type type() {
	return typeNode.type();
    }
    public String name() {
	return name;
    }
    public Location location() {
	return typeNode.location();
    }
    protected void _dump(Dumper d) {
	d.printMember("name", name);
	d.printMember("typeNode", typeNode);
    }
}
