package cflat.entity;

import cflat.ast.TypeNode;
import cflat.ast.Dumper;

public class Parameter extends DefinedVariable {
    public Parameter(TypeNode type, String name) {
	super(false, type, name, null);
    }
    
    protected void _dump(Dumper d) {
	d.printMember("name", name);
	d.printMember("typeNode", typeNode);
    }
}
