package cflat.ir;

//import cflat.ast.Location;
import cflat.asm.Type;
import java.util.*;

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
    public <S,E> E accept(IRVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
    protected void _dump(Dumper d) {
	d.printMember("expr", expr);
	d.printMembers("args", args);
    }
}
