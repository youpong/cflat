package cflat.ir;

import cflat.ast.Location;

// TODO: test
/**
 * 代入
 */
public class Assign extends Stmt {
    /** 代入の左辺 */
    protected Expr lhs;
    /** 代入の右辺 */
    protected Expr rhs;

    public Assign(Location loc, Expr lhs, Expr rhs) {
        super(loc);
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Expr lhs() {
        return lhs;
    }

    public Expr rhs() {
        return rhs;
    }

    public <S, E> S accept(IRVisitor<S, E> visitor) {
        return visitor.visit(this);
    }

    protected void _dump(Dumper d) {
        d.printMember("lhs", lhs);
        d.printMember("rhs", rhs);
    }
}
