package cflat.entity;

import cflat.asm.ImmediateValue;
import cflat.asm.MemoryReference;

// TODO: implement
public class ConstantEntry {
    protected String value;
    // protected Symbol symbol;
    protected MemoryReference memref;
    protected ImmediateValue address;

    public ConstantEntry(String val) {
        value = val;
    }

    // ...

    public MemoryReference memref() {
        if (this.memref == null) {
            throw new Error("must not happen: memref == null");
        }
        return this.memref;
    }

    // ...

    public ImmediateValue address() {
        return this.address;
    }
}
