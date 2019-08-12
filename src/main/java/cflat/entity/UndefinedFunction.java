package cflat.entity;

import cflat.ast.Dumper;
import cflat.ast.TypeNode;

public class UndefinedFunction extends Function {
    protected Params params;
    
    public UndefinedFunction(TypeNode t, String name, Params params) {
	super(false, t, name);
	this.params = params;
    }

    protected void _dump(Dumper d) {
	d.printMember("name", name);
	d.printMember("isPrivate", isPrivate);
	d.printMember("typeNode", typeNode);
	d.printMember("params", params);
    }
}
