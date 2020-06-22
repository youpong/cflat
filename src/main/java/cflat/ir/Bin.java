package cflat.ir;

import cflat.asm.Type;

// TODO: test
/**
 * 二項演算 (l OP r)
 */
public class Bin extends Expr {
    /** 演算の種類 */
    protected Op op;
    /** 左の式 */
    protected Expr left;
    /** 右の式 */
    protected Expr right;

    /**
     * 二項演算の中間表現
     * 
     * @param type
     *            この式の型
     * @param op
     *            二項演算の種類
     * @param left
     *            二項演算の左の式(x + y の x)
     * @param right
     *            二項演算の右の式(x + y の y)
     */
    public Bin(Type type, Op op, Expr left, Expr right) {
        super(type);
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public Expr left() {
        return left;
    }

    public Expr right() {
        return right;
    }

    public Op op() {
        return op;
    }

    public <S, E> E accept(IRVisitor<S, E> visitor) {
        return visitor.visit(this);
    }

    protected void _dump(Dumper d) {
        d.printMember("op", "" + op);
        d.printMember("left", left);
        d.printMember("right", right);
    }
}
