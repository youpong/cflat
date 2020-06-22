package cflat.ast;

// TODO: implement
public class UnaryArithmeticOpNode extends UnaryOpNode {
    protected long amount;

    public UnaryArithmeticOpNode(String op, ExprNode expr) {
        super(op, expr);
        amount = 1;
    }
}
