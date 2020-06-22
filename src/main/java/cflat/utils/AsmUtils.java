package cflat.utils;

// TODO: test
public final class AsmUtils {
    private AsmUtils() {
    }

    static public long align(long n, long alignment) {
        return (n + alignment - 1) / alignment * alignment;
    }
}
