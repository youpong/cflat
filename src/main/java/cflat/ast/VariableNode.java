package cflat.ast;

import cflat.type.Type;
import cflat.entity.Entity;

// TODO: implement
public class VariableNode extends LHSNode {
    private Location location;
    private String name;
    private Entity entity;
    
    public VariableNode(Location loc, String name) {
	this.location = loc;
	this.name = name;
    }

    public boolean isResolved() {
	//return (entity != null);
	return false;
    }
    /*
    public Entity entity() {
	if (entity == null) 
	    throw new Error("VariableNode.entity == null");
	return entity;
    }
    */

    public void setEntity(Entity entity) {
	this.entity = entity;
    }
    // TODO: implement
    protected Type origType() {
	//return entity().type();
	return null;
    }
    public String name() {
	return name;
    }
    public Location location() {
	return location;
    }
    protected void _dump(Dumper d) {
	if (type != null)
	    d.printMember("type", type);
	d.printMember("name", name, isResolved());
    }
    
    public <S,E> E accept(ASTVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
}
