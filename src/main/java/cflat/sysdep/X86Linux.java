package cflat.sysdep;

import cflat.type.TypeTable;

// TODO: implement
public class X86Linux implements Platform {
    public TypeTable typeTable() {
        return TypeTable.ilp32();
    }
}
