package cflat.asm;

abstract public class MemoryReference extends Operand
        implements
            Comparable<MemoryReference> {

    // ...
    // public boolean isMemoryReference()

    abstract public void fixOffset(long diff);
    abstract protected int cmp(DirectMemoryReference mem);
    abstract protected int cmp(IndirectMemoryReference mem);
}
