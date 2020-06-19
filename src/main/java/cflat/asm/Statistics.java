package cflat.asm;

import java.util.Map;
import java.util.HashMap;

public class Statistics {
    protected Map<Register, Integer> registerUsage;
    //    protected Map<String, Integer> insnUsage;
    protected Map<Symbol, Integer> symbolUsage;

    public Statistics() {
	registerUsage = new HashMap<Register, Integer>();
    }

    // TODO
    public void registerUsed(Register reg) {
	incrementCount(registerUsage, reg);
    }

    // ...

    public void symbolUsed(Symbol sym) {
	incrementCount(symbolUsage, sym);
    }

    protected <K> int fetchCount(Map<K, Integer> m, K key) {
	Integer n = m.get(key);
	if (n == null) {
	    return 0;
	} else {
	    return n;
	}
    }

    protected <K> void incrementCount(Map<K, Integer> m, K key) {
	m.put(key, fetchCount(m, key) + 1);
    }
}
