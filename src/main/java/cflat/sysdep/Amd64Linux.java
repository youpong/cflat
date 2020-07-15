package cflat.sysdep;

import cflat.type.TypeTable;
import cflat.utils.ErrorHandler;
import cflat.asm.Type;

// TODO: implement
public class Amd64Linux implements Platform {
    public TypeTable typeTable() {
        //        return TypeTable.ilp32();
        return null;
    }

    // TODO: implement
    public CodeGenerator codeGenerator(CodeGeneratorOptions opts, ErrorHandler h) {
        //    return new cflat.sysdep.amd64.CodeGenerator(opts, naturalType(), h);
        return null;
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
