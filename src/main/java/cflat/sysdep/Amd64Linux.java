package cflat.sysdep;

import cflat.type.TypeTable;
import cflat.utils.ErrorHandler;
import cflat.asm.Type;

// TODO: test
public class Amd64Linux implements Platform {
    public TypeTable typeTable() {
        return TypeTable.lp64();
    }

    public CodeGenerator codeGenerator(CodeGeneratorOptions opts, ErrorHandler h) {
        return new cflat.sysdep.amd64.CodeGenerator(opts, naturalType(), h);
    }

    private Type naturalType() {
        return Type.INT64;
    }

    public Assembler assembler(ErrorHandler h) {
        return new GNUAssembler(h);
    }

    public Linker linker(ErrorHandler h) {
        return new GNULinker(h);
    }
}
