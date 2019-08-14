package cflat.ast;

public class GotoNode extends StmtNode {
    protected String target;
    
    public GotoNode(Location loc, String target) {
	super(loc);
	this.target = target;
    }
    protected void _dump(Dumper d) {
	d.printMember("target", target);
    }
    public <S,E> S accept(ASTVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
}
	    
