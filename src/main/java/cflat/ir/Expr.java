package cflat.ir;

import cflat.asm.Type;

abstract public class Expr implements Dumpable {
    final Type type;

    Expr(Type type) {
	this.type = type;
    }
    
    public void dump(Dumper d) {
	d.printClass(this);
	d.printMember("type", type);
	_dump(d);
    }
    abstract protected void _dump(Dumper d);
}
