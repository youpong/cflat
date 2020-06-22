package cflat.asm;

public class ImmediateValue extends Operand {
    protected Literal expr;

    public ImmediateValue(long n) {
        this(new IntegerLiteral(n));
    }

    public ImmediateValue(Literal expr) {
        this.expr = expr;
    }

    // ...

    public void collectStatistics(Statistics stats) {
        // does nothing
    }

    // ...

    public String toSource(SymbolTable table) {
        return "$" + expr.toSource(table);
    }

    public String dump() {
        return "ImmediateValue " + expr.dump() + ")";
    }
}
