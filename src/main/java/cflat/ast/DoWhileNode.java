package cflat.ast;

public class DoWhileNode extends StmtNode {
    protected ExprNode cond;
    protected StmtNode body;
    public DoWhileNode(Location loc, StmtNode b, ExprNode c) {
	super(loc);
	this.body = b;	
	this.cond = c;
    }
    protected void _dump(Dumper d) {
	d.printMember("body", body);	
	d.printMember("cond", cond);
    }
    public <S,E> S accept(ASTVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
}
