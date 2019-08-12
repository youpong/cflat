package cflat.ast;

public class Slot extends Node {
    protected TypeNode typeNode;
    protected String name;
    
    public Slot(TypeNode t, String n) {
	this.typeNode = t;
	this.name = n;
    }
    public Location location() {
	return typeNode.location();
    }
    protected void _dump(Dumper d) {
	d.printMember("name", name);
	d.printMember("typeNode", typeNode);
    }
}
