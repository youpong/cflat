package cflat.sysdep;

import cflat.type.TypeTable;
import cflat.utils.ErrorHandler;
import cflat.asm.Type;

// TODO: implement
public class X86Linux implements Platform {
    public TypeTable typeTable() {
        return TypeTable.ilp32();
    }

    public CodeGenerator codeGenerator(CodeGeneratorOptions opts, ErrorHandler h) {
        return new cflat.sysdep.x86.CodeGenerator(opts, naturalType(), h);
    }

    private Type naturalType() {
        return Type.INT32;
    }

    // ...
}
