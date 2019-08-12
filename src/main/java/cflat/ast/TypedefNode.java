package cflat.ast;

import cflat.type.TypeRef;
import cflat.type.UserTypeRef;

public class TypedefNode extends TypeDefinition {
    protected TypeNode real;
    
    public TypedefNode(Location loc, TypeRef real, String name) {
	super(loc, new UserTypeRef(name), name);
	this.real = new TypeNode(real);
    }

    protected void _dump(Dumper d) {
	d.printMember("name", name);
	d.printMember("typeNode", typeNode);
    }
}
