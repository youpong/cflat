package cflat.ir;

import cflat.asm.Type;

public class Uni extends Expr {
    protected Op op;
    protected Expr expr;

    public Uni(Type type, Op op, Expr expr) {
	super(type);
	this.op = op;
	this.expr = expr;
    }

    public <S,E> E accept(IRVisitor<S,E> visitor) {
	return visitor.visit(this);
    }

    public void _dump(Dumper d) {
	d.printMember("op", "" + op);
	d.printMember("expr", expr);
    }
}
