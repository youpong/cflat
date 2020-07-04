package cflat.sysdep;

import cflat.exception.IPCException;

public interface Assembler {
    void assemble(String srcPath, String destPath, AssemblerOptions opts)
            throws IPCException;
}
