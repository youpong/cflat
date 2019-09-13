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
    //    public boolean isAssignable()
    public boolean isDefined() { return true; }
    //    public boolean isInitialized()
    public boolean isConstant() { return true; }
    public ExprNode value() {
	return value();
    }
    
    protected void _dump(Dumper d) {
	d.printMember("name", name);
	d.printMember("typeNode", typeNode);
	d.printMember("value", value);
    }

    public <T> T accept(EntityVisitor<T> visitor) {
	return visitor.visit(this);
    }
}
