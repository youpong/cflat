package cflat.ast;

import cflat.type.TypeRef;
import cflat.type.Type;

abstract public class TypeDefinition extends Node {
    protected String name;
    protected Location location;
    protected TypeNode typeNode;
    
    public TypeDefinition(Location loc, TypeRef ref, String name) {
	this.name = name;
	this.location = loc;
	this.typeNode = new TypeNode(ref);
    }

    public String name() {
	return name;
    }
    public Location location() {
	return location;
    }

    public TypeRef typeRef() {
	return typeNode.typeRef();
    }

    abstract public Type definitingType();
    abstract public <T> T accept(DeclarationVisitor<T> visitor);
}
