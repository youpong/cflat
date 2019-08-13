package cflat.ast;

public class ExprStmtNode extends StmtNode {
    protected ExprNode expr;

    public ExprStmtNode(Location loc, ExprNode expr) {
	super(loc);
	this.expr = expr;
    }
    protected void _dump(Dumper d) {
	d.printMember("expr", expr);
    }
    public <S,E> S accept(ASTVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
}
