package cflat.ir;

import cflat.asm.Label;
import cflat.ast.Location;

/**
 * 無条件ジャンプ
 */
public class Jump extends Stmt {
    /** ジャンプ先 */
    protected Label label;

    public Jump(Location loc, Label label) {
        super(loc);
        this.label = label;
    }

    public Label label() {
        return label;
    }

    public <S, E> S accept(IRVisitor<S, E> visitor) {
        return visitor.visit(this);
    }

    protected void _dump(Dumper d) {
        d.printMember("label", label);
    }
}
