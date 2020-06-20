package cflat.ir;

import cflat.asm.Type;

/**
 * 単項演算 (OP e)
 */
public class Uni extends Expr {
    /** 演算の種類 */
    protected Op op;
    /** 演算を適用する式 */
    protected Expr expr;

    public Uni(Type type, Op op, Expr expr) {
	super(type);
	this.op = op;
	this.expr = expr;
    }

    public Op op() { return op; }
    public Expr expr() { return expr; }
    
    public <S,E> E accept(IRVisitor<S,E> visitor) {
	return visitor.visit(this);
    }

    public void _dump(Dumper d) {
	d.printMember("op", "" + op);
	d.printMember("expr", expr);
    }
}
