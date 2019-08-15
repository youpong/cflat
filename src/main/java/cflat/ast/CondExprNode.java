package cflat.ast;

import cflat.type.Type;

public class CondExprNode extends ExprNode {
    protected ExprNode cond, thenExpr, elseExpr;
    public CondExprNode(ExprNode cond, ExprNode t, ExprNode e) {
	super();
	this.cond = cond;
	this.thenExpr = t;
	this.elseExpr = e;
    }
    public Type type() {
	return thenExpr.type();
    }
    public ExprNode cond() {
	return cond;
    }
    public ExprNode thenExpr() {
	return thenExpr;
    }
    public ExprNode elseExpr() {
	return elseExpr;
    }
    public Location location() {
	return cond.location();
    }
    protected void _dump(Dumper d) {
	d.printMember("cond", cond);
	d.printMember("thenExpr", thenExpr);
	d.printMember("elseExpr", elseExpr);
    }
    public <S,E> E accept(ASTVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
}
