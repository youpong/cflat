package cflat.ir;

import cflat.asm.MemoryReference;
import cflat.asm.Operand;
import cflat.asm.Type;
import cflat.entity.Entity;

/**
 * 変数参照
 * VariableNode の r-value の中間表現
 */
public class Var extends Expr {
    /** DefinedVariable オブジェクト */
    protected Entity entity;

    public Var(Type type, Entity entity) {
	super(type);
	this.entity = entity;
    }

    public boolean isVar() { return true; }
    
    // ...

    // 27
    public Operand address() {
	return entity.address();
    }
    
    public MemoryReference memref() {
	return entity.memref();
    }

    public Addr addressNode(Type type) {
	return new Addr(type, entity);
    }
    
    // ...

    // 45
    public <S,E> E accept(IRVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
    protected void _dump(Dumper d) {
	d.printMember("entity", entity.name());
    }
}
