package cflat.asm;

import java.util.Map;
import java.util.HashMap;

public class SymbolTable {
    protected String base;
    protected Map<UnnamedSymbol, String> map;
    protected long seq = 0;

    public SymbolTable(String base) {
	this.base = base;
	this.map = new HashMap<UnnamedSymbol, String>();
    }

    // ...
    public String symbolString(UnnamedSymbol sym) {
	String str = map.get(sym);
	if (str != null) {
	    return str;
	} else {
	    String newStr = newString();
	    map.put(sym, newStr);
	    return newStr;
	}
    }

    protected String newString() {
	return base + seq++;
    }
}
