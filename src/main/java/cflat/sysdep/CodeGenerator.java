package cflat.sysdep;

public interface CodeGenerator {
    AssemblyCode generate(cflat.ir.IR ir);
}
