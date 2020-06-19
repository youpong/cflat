package cflat.asm;

abstract public class MemoryReference extends Operand
    implements Comparable<MemoryReference> {
    
    // ...
    
    abstract protected int cmp(DirectMemoryReference mem);
    abstract protected int cmp(IndirectMemoryReference mem);    
}
