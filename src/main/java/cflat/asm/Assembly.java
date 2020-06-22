package cflat.asm;

abstract public class Assembly {
    abstract public String toSource(SymbolTable table);

    abstract public String dump();
}
