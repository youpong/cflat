package cflat.ast;

public class DoWhileNode extends StmtNode {
    protected StmtNode body;
    protected ExprNode cond;

    public DoWhileNode(Location loc, StmtNode b, ExprNode c) {
        super(loc);
        this.body = b;
        this.cond = c;
    }

    public StmtNode body() {
        return body;
    }

    public ExprNode cond() {
        return cond;
    }

    protected void _dump(Dumper d) {
        d.printMember("body", body);
        d.printMember("cond", cond);
    }

    public <S, E> S accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }
}
