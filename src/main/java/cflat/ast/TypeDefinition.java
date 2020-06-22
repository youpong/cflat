package cflat.ast;

import cflat.type.Type;
import cflat.type.TypeRef;

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

    public TypeNode typeNode() {
        return typeNode;
    }

    public TypeRef typeRef() {
        return typeNode.typeRef();
    }

    abstract public Type definitingType();

    abstract public <T> T accept(DeclarationVisitor<T> visitor);
}
