package cflat.asm;

public class UnnamedSymbol extends BaseSymbol {
    public UnnamedSymbol() {
	super();
    }

    // ...
    
    public String toSource() {
	throw new Error("UnnamedSymbol#toSource() called");
    }

    public String toSource(SymbolTable table) {
	return table.symbolString(this);
    }
    
    // ...

    public int compareTo(Literal lit) {
	return -(lit.compareTo(this));
    }

    public int cmp(IntegerLiteral i) {
	return 1;
    }
    
    // ...
    
    // 44
    public String dump() {
	return "(UnnamedSymbol @" + Integer.toHexString(hashCode()) + ")";
    }
}
