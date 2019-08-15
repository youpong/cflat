package cflat.ast;

public class ForNode extends StmtNode {
    protected ExprNode init, cond, incr;
    protected StmtNode body;
    
    public ForNode(Location loc,
		   ExprNode init, ExprNode cond, ExprNode incr, StmtNode body) {
	super(loc);
	this.init = init;
	this.cond = cond;
	this.incr = incr;
	this.body = body;
    }
    public ExprNode init() {
	return init;
    }
    public ExprNode cond() {
	return cond;
    }
    public ExprNode incr() {
	return incr;
    }
    public StmtNode body() {
	return body;
    }
    protected void _dump(Dumper d) {
	d.printMember("init", init);
	d.printMember("cond", cond);	
	d.printMember("incr", incr);
	d.printMember("body", body);
    }
    public <S,E> S accept(ASTVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
}
