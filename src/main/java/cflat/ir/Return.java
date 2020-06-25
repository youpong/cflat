package cflat.ir;

import cflat.ast.Location;

/**
 * return
 */
public class Return extends Stmt {
    /** 返り値を表す式 */
    protected Expr expr;

    public Return(Location loc, Expr expr) {
        super(loc);
        this.expr = expr;
    }

    public Expr expr() {
        return expr;
    }

    public <S, E> S accept(IRVisitor<S, E> visitor) {
        return visitor.visit(this);
    }

    protected void _dump(Dumper d) {
        d.printMember("expr", expr);
    }
}
