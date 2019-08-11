package cflat.ast;

import cflat.type.Type;

/**
 * Cast operator
 */
public class CastNode extends ExprNode {
    protected TypeNode typeNode;
    protected ExprNode expr;

    public CastNode(Type t, ExprNode expr) {
	this(new TypeNode(t), expr);
    }

    public CastNode(TypeNode t, ExprNode expr) {
	this.typeNode = t;
	this.expr = expr;
    }
    public Type type() {
	return typeNode.type();
    }
    public Location location() {
	return typeNode.location();
    }
    protected void _dump(Dumper d) {
	d.printMember("typeNode", typeNode);
	d.printMember("expr", expr);
    }
    public <S,E> E accept(ASTVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
}
