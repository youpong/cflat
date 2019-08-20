package cflat.entity;

import cflat.ast.Dumper;
import cflat.ast.TypeNode;
import java.util.*;

public class UndefinedFunction extends Function {
    protected Params params;
    
    public UndefinedFunction(TypeNode t, String name, Params params) {
	super(false, t, name);
	this.params = params;
    }
    public List<Parameter> parameters() {
	return params.parameters();
    }
    protected void _dump(Dumper d) {
	d.printMember("name", name);
	d.printMember("isPrivate", isPrivate);
	d.printMember("typeNode", typeNode);
	d.printMember("params", params);
    }

    public <T> T accept(EntityVisitor<T> visitor) {
	return visitor.visit(this);
    }
}
