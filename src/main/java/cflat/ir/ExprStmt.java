package cflat.ir;

import cflat.ast.Location;

/**
 * 式1つだけの文
 */
public class ExprStmt extends Stmt {
    /** 実行する式 */
    protected Expr expr;

    public ExprStmt(Location loc, Expr expr) {
        super(loc);
        this.expr = expr;
    }

    /*
     * public Expr expr() { return expr; }
     */
    public <S, E> S accept(IRVisitor<S, E> visitor) {
        return visitor.visit(this);
    }

    protected void _dump(Dumper d) {
        d.printMember("expr", expr);
    }
}
