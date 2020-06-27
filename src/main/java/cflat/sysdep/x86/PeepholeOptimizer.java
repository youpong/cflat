package cflat.sysdep.x86;

public class PeepholeOptimizer {
    // ...

    // 59
    static public PeepholeOptimizer defaultSet() {
        PeepholeOptimizer set = new PeepholeOptimizer();
        set.loadDefaultFilters();
        return set;
    }

    private void loadDefaultFilters() {
        // TODO
    }

    // ...
}
