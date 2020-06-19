package cflat.asm;

public class IntegerLiteral implements Literal {
    protected long value;

    public IntegerLiteral(long n) {
	this.value = n;
    }

    // ...

    public boolean isZero() {
	return value == 0;
    }

    // ...

    public String toSource() {
	return new Long(value).toString();
    }

    public String toSource(SymbolTable table) {
	return toSource();
    }

    public void collectStatistics(Statistics stats) {
	// does nothing
    }

    // ...

    public int compareTo(Literal lit) {
	return -(lit.cmp(this));
    }

    public int cmp(IntegerLiteral i) {
	return new Long(value).compareTo(new Long(i.value));
    }

    // ...

    public String dump() {
	return "(IntegerLiteral " + new Long(value).toString() + ")";
    }
}
