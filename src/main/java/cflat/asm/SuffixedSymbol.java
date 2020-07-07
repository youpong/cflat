package cflat.asm;

import cflat.utils.TextUtils;

/**
 * suffixed symbol used in Position independent code
 */
public class SuffixedSymbol implements Symbol {
    protected Symbol base;
    protected String suffix;

    public SuffixedSymbol(Symbol base, String suffix) {
        this.base = base;
        this.suffix = suffix;
    }

    public boolean isZero() {
        return false;
    }

    public void collectStatistics(Statistics stats) {
        base.collectStatistics(stats);
    }

    // ...

    public String name() {
        return base.name();
    }

    public String toSource() {
        return base.toSource() + suffix;
    }

    public String toSource(SymbolTable table) {
        return base.toSource(table) + suffix;
    }

    // TODO: test
    public String toString() {
        return base.toString() + suffix;
    }

    public int compareTo(Literal lit) {
        return -(lit.compareTo(this));
    }

    public int cmp(IntegerLiteral i) {
        return 1;
    }

    // ...

    public String dump() {
        return "(SuffixedSymbol " + base.dump() + " " + TextUtils.dumpString(suffix)
                + ")";
    }
}
