package cflat.ir;

import cflat.asm.Label;
import cflat.ast.Location;

/**
 * 無条件ジャンプ
 */
public class Jump extends Stmt {
    /** ジャンプ先 */
    protected Label target;

    public Jump(Location loc, Label target) {
        super(loc);
        this.target = target;
    }

    public <S, E> S accept(IRVisitor<S, E> visitor) {
        return visitor.visit(this);
    }

    protected void _dump(Dumper d) {
        d.printMember("target", target);
    }
}
