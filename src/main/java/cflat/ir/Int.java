package cflat.ir;

import cflat.asm.ImmediateValue;
import cflat.asm.IntegerLiteral;
import cflat.asm.MemoryReference;
import cflat.asm.Type;

// TODO: test
/**
 * 整数定数
 */
public class Int extends Expr {
    /** 値 */
    protected long value;

    public Int(Type type, long value) {
	super(type);
	this.value = value;
    }

    public long value() { return value; }

    public boolean isConstant() { return true; }

    public ImmediateValue asmValue() {
	return new ImmediateValue(new IntegerLiteral(value));
    }
    
    public MemoryReference memref() {
	throw new Error("must not happen: IntValue#memref");
    }
    
    public <S,E> E accept(IRVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
    
    protected void _dump(Dumper d) {
	d.printMember("value", value);
    }
}
