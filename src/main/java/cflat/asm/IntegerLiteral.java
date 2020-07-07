package cflat.asm;

/**
 * Integer Literal
 */
public class IntegerLiteral implements Literal {
    protected long value;

    public IntegerLiteral(long n) {
        this.value = n;
    }

    // TODO: test
    public boolean equals(Object other) {
        return (other instanceof IntegerLiteral) && equals((IntegerLiteral) other);
    }

    // TODO: test    
    public boolean equals(IntegerLiteral other) {
        return other.value == this.value;
    }
    /*
    public long value() {
    return value;
    }
    */
    public boolean isZero() {
        return value == 0;
    }

    // ...

    public String toSource() {
        return Long.valueOf(value).toString();
    }

    public String toSource(SymbolTable table) {
        return toSource();
    }

    public void collectStatistics(Statistics stats) {
        // does nothing
    }

    // TODO: test
    public String toString() {
        return Long.valueOf(value).toString();
    }

    public int compareTo(Literal lit) {
        return -(lit.cmp(this));
    }

    public int cmp(IntegerLiteral i) {
        return Long.valueOf(value).compareTo(Long.valueOf(i.value));
    }

    // ...

    public String dump() {
        return "(IntegerLiteral " + Long.valueOf(value).toString() + ")";
    }
}
