package cflat.entity;

import cflat.ast.Dumper;
import cflat.ast.TypeNode;
import cflat.ast.ExprNode;

public class Constant extends Entity {
    private ExprNode value;
    
    public Constant(TypeNode type, String name, ExprNode value) {
	super(true, type, name);
	this.value = value;
    }
    public ExprNode value() {
	return value();
    }
    protected void _dump(Dumper d) {
	d.printMember("name", name);
	d.printMember("typeNode", typeNode);
	d.printMember("value", value);
    }
	
}
