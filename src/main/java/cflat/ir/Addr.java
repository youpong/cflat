package cflat.ir;

import cflat.asm.MemoryReference;
import cflat.asm.Type;
import cflat.entity.Entity;

/**
 * アドレス取得 (&amp;var) VariableNode の l-value の中間表現
 */
public class Addr extends Expr {
    /** アドレスを得る式 */
    Entity entity;

    public Addr(Type type, Entity entity) {
        super(type);
        this.entity = entity;
    }

    public boolean isAddr() {
        return true;
    }

    public Entity entity() {
        return entity;
    }

    // ...

    public MemoryReference memref() {
        return entity.memref();
    }

    public Entity getEntityForce() {
        return entity;
    }

    public <S, E> E accept(IRVisitor<S, E> visitor) {
        return visitor.visit(this);
    }

    protected void _dump(Dumper d) {
        d.printMember("entity", entity.name());
    }
}
