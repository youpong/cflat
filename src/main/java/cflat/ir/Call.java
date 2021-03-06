package cflat.ir;

import cflat.asm.Type;
import cflat.entity.Function;
import cflat.entity.Entity;
import java.util.List;

// TODO: test
/**
 * 関数呼び出し
 */
public class Call extends Expr {
    /** 関数を表す式 */
    private Expr expr;
    /** 引数のリスト */
    private List<Expr> args;

    public Call(Type type, Expr expr, List<Expr> args) {
        super(type);
        this.expr = expr;
        this.args = args;
    }

    public Expr expr() {
        return expr;
    }

    public List<Expr> args() {
        return args;
    }

    public long numArgs() {
        return args.size();
    }

    /** Returns true if this funcall is NOT a function pointer call. */
    public boolean isStaticCall() {
        return (expr.getEntityForce() instanceof Function);
    }

    /**
     * Returns a function object which is refered by expression.
     * This method expects this is static function call(isStaticCall()).
     */
    public Function function() {
        Entity ent = expr.getEntityForce();
        if (ent == null) {
            throw new Error("not a static funcall");
        }
        return (Function) ent;
    }

    public <S, E> E accept(IRVisitor<S, E> visitor) {
        return visitor.visit(this);
    }

    protected void _dump(Dumper d) {
        d.printMember("expr", expr);
        d.printMembers("args", args);
    }
}
