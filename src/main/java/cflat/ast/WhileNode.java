package cflat.ast;

public class WhileNode extends StmtNode {
    protected ExprNode cond;
    protected StmtNode body;
    public WhileNode(Location loc, ExprNode c, StmtNode b) {
	super(loc);
	this.cond = c;
	this.body = b;
    }
    public ExprNode cond() {
	return cond;
    }
    public StmtNode body() {
	return body;
    }
    protected void _dump(Dumper d) {
	d.printMember("cond", cond);
	d.printMember("body", body);
    }
    public <S,E> S accept(ASTVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
}
