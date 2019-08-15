package cflat.ast;

import cflat.type.Type;

/**
 * Dereference pointer "*"
 */
public class DereferenceNode extends LHSNode {
    private ExprNode expr;
    
    public DereferenceNode(ExprNode expr) {
	this.expr = expr;
    }

    protected Type origType() {
	return expr.type().baseType();
    }
    public ExprNode expr() {
	return expr;
    }
    public Location location() {
	return expr.location();
    }
    protected void _dump(Dumper d) {
	if (type != null)
	    d.printMember("type", type);
	d.printMember("expr", expr);
    }
    public <S,E> E accept(ASTVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
}
