package cflat.ir;

import cflat.asm.Type;
import cflat.entity.Entity;

public class Addr extends Expr {
    Entity entity;

    public Addr(Type type, Entity entity) {
	super(type);
	this.entity = entity;
    }
    public <S,E> E accept(IRVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
    protected void _dump(Dumper d) {
	d.printMember("entity", entity.name());
    }
}
