package cflat.asm;

import cflat.utils.TextUtils;

/**
 * named symbol appears in C flat souce code.
 */
public class NamedSymbol extends BaseSymbol {
    protected String name;

    public NamedSymbol(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public String toSource() {
        return name;
    }

    public String toSource(SymbolTable table) {
        return name;
    }

    // TODO: test
    public String toString() {
        return "#" + name;
    }

    public int compareTo(Literal lit) {
        return -(lit.compareTo(this));
    }

    public int cmp(IntegerLiteral i) {
        return 1;
    }

    // ...

    public String dump() {
        return "(NamedSymbol " + TextUtils.dumpString(name) + ")";
    }
}
