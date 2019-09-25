package cflat.ir;

import cflat.ast.Location;
import cflat.asm.Label;

/**
 * 条件付きジャンプ
 */
public class CJump extends Stmt {
    /** 条件式 */
    protected Expr cond;
    /** 条件が真の場合のジャンプ先 */
    protected Label thenLabel;
    /** 条件が偽の場合のジャンプ先 */
    protected Label elseLabel;

    public CJump(Location loc, Expr cond, Label thenLabel, Label elseLabel) {
	super(loc);
	this.cond = cond;
	this.thenLabel = thenLabel;
	this.elseLabel = elseLabel;
    }

    public <S,E> S accept(IRVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
    protected void _dump(Dumper d) {
	d.printMember("cond", cond);
	d.printMember("thenLabel", thenLabel);
	d.printMember("elseLabel", elseLabel);
    }
}

