package cflat.asm;

public class Label extends Assembly {
    protected Symbol symbol;

    public String toSource(SymbolTable table) {
	return symbol.toSource(table) + ":";
    }
    public String dump() {
	return "(Label " + symbol.dump() + ")";
    }
}
