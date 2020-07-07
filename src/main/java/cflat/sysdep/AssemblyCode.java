package cflat.sysdep;

import java.io.PrintStream;

/**
 * manage list of Assembly instance
 */
public interface AssemblyCode {
    String toSource();

    void dump();

    void dump(PrintStream s);
}
