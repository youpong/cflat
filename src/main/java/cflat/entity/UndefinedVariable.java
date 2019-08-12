package cflat.entity;

import cflat.ast.Dumper;
import cflat.ast.TypeNode;

public class UndefinedVariable extends Variable {
    public UndefinedVariable(TypeNode t, String name) {
	super(false, t, name);
    }

    public boolean isPrivate() { return false; }
    
    protected void _dump(Dumper d) {
	d.printMember("name", name);
	d.printMember("isPrivate", isPrivate());
	d.printMember("typeNode", typeNode);
    }

}
