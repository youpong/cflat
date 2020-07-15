package cflat.asm;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Statistics {
    protected Map<Register, Integer> registerUsage;
    // protected Map<String, Integer> insnUsage;
    protected Map<Symbol, Integer> symbolUsage;

    static public Statistics collect(List<Assembly> assemblies) {
        Statistics stats = new Statistics();
        for (Assembly asm : assemblies) {
            asm.collectStatistics(stats);
        }
        return stats;
    }

    public Statistics() {
        // TODO
        registerUsage = new HashMap<Register, Integer>();
        // insnUsage = new HashMap<String, Integer>();
        symbolUsage = new HashMap<Symbol, Integer>();
    }

    public boolean doesRegisterUsed(Register reg) {
        return numRegisterUsed(reg) > 0;
    }

    public int numRegisterUsed(Register reg) {
        return fetchCount(registerUsage, reg);
    }

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
