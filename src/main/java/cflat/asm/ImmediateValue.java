package cflat.asm;

/**
 * Operand of Immediate value lie $0
 */
public class ImmediateValue extends Operand {
    protected Literal expr;

    public ImmediateValue(long n) {
        this(new IntegerLiteral(n));
    }

    public ImmediateValue(Literal expr) {
        this.expr = expr;
    }

    // TODO: test
    public boolean equals(Object other) {
        if (!(other instanceof ImmediateValue))
            return false;
        ImmediateValue imm = (ImmediateValue) other;
        return expr.equals(imm.expr);
    }

    /*
    public Literal expr() {
    return expr;
    }
    */

    public void collectStatistics(Statistics stats) {
        // does nothing
    }

    public String toSource(SymbolTable table) {
        return "$" + expr.toSource(table);
    }

    public String dump() {
        return "ImmediateValue " + expr.dump() + ")";
    }
}
