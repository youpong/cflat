package cflat.entity;

import cflat.asm.ImmediateValue;
import cflat.asm.MemoryReference;
import cflat.asm.Symbol;

// TODO: implement
public class ConstantEntry {
    protected String value;
    protected Symbol symbol;
    protected MemoryReference memref;
    protected ImmediateValue address;

    public ConstantEntry(String val) {
        value = val;
    }

    public String value() {
        return value;
    }
    /*
    public void setSymbol(Symbol sym) {
    this.symbol = sym;
    }
    */
    public Symbol symbol() {
        if (symbol == null) {
            throw new Error("must not happen: symbol == null");
        }
        return symbol;
    }

    //    public void setMemref(MemoryReference mem) {

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
