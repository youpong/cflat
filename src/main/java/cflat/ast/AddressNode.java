package cflat.ast;

import cflat.type.Type;

/**
 * Address operator "&amp;"
 */
public class AddressNode extends ExprNode {
    final ExprNode expr;
    Type type;
    
    public AddressNode(ExprNode expr) {
	this.expr = expr;
    }
    public Type type() {
	if(type==null)
	    throw new Error("type is null");
	return type;
    }
    public ExprNode expr() {
	return expr;
    }
    public Location location() {
	return expr.location();
    }

    protected void _dump(Dumper d) {
	if(type != null)
	    d.printMember("type", type);
	d.printMember("expr", expr);
    }
    public <S,E> E accept(ASTVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
}
