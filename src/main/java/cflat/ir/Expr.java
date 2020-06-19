package cflat.ir;

import cflat.asm.Type;
import cflat.asm.ImmediateValue;
import cflat.asm.MemoryReference;

abstract public class Expr implements Dumpable {
    final Type type;

    Expr(Type type) {
	this.type = type;
    }

    public Type type() { return type; }

    public boolean isVar() { return false; }
    
    //public boolean isAddr() { return false; }

    public boolean isConstant() { return false; }

    public ImmediateValue asmValue() {
	throw new Error("Expr#asmValue called");
    }
    
    // ...

    public MemoryReference memref() {
	throw new Error("Expr#memref called");
    }
    
    public Expr addressNode(Type type) {
	throw new Error("unexpected node for LHS: " + getClass());
    }
    
    // ...

    abstract public <S,E> E accept(IRVisitor<S,E> visitor);
    
    public void dump(Dumper d) {
	d.printClass(this);
	d.printMember("type", type);
	_dump(d);
    }
    
    abstract protected void _dump(Dumper d);
}
