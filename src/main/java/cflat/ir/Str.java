package cflat.ir;

import cflat.entity.ConstantEntry;
import cflat.asm.Type;

/**
 * 文字列定数
 */
public class Str extends Expr {
    /** ConstantEntry オブジェクト */
    protected ConstantEntry entry;

    public Str(Type type, ConstantEntry entry) {
	super(type);
	this.entry = entry;
    }

    //    public ConstantEntry entry() { return entry; }
    //
    public boolean isConstant() { return true; }
    //
    public <S,E> E accept(IRVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
    protected void _dump(Dumper d) {
	d.printMember("entry", "" + entry);
    }
}
