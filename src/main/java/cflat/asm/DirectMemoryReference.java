package cflat.asm;

public class DirectMemoryReference extends MemoryReference {
    protected Literal value;
    
    public DirectMemoryReference(Literal val) {
	this.value = val;
    }
    
    // ...

    public void collectStatistics(Statistics stats) {
	value.collectStatistics(stats);
    }

    // ...

    public String toSource(SymbolTable table) {
	return this.value.toSource(table);
    }

    public int compareTo(MemoryReference mem) {
	return -(mem.cmp(this));
    }
    
    protected int cmp(IndirectMemoryReference mem) {
	return 1;
    }

    protected int cmp(DirectMemoryReference mem) {
	return value.compareTo(mem.value);
    }
    
    public String dump() {
	return "(DirectMemoryReference " + value.dump() + ")";
    }
}
