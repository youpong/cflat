package cflat.ir;

import cflat.asm.Type;
import cflat.entity.Entity;

public class Var extends Expr {
    protected Entity entity;

    public Var(Type type, Entity entity) {
	super(type);
	this.entity = entity;
    }

    public boolean isVar() { return true; }
    //
    public Addr addressNode(Type type) {
	return new Addr(type, entity);
    }
    //
    public <S,E> E accept(IRVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
    protected void _dump(Dumper d) {
	d.printMember("entity", entity.name());
    }
}
