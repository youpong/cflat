package cflat.asm;

/**
 * Base Symbol
 */
abstract public class BaseSymbol implements Symbol {

    public boolean isZero() {
        return false;
    }

    public void collectStatistics(Statistics stats) {
        stats.symbolUsed(this);
    }

    // ...
}
