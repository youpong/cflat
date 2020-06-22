package cflat.ast;

import cflat.type.Type;

/**
 * Array 参照 ary[i]
 */
public class ArefNode extends LHSNode {
    private ExprNode expr, index;

    public ArefNode(ExprNode expr, ExprNode idx) {
        this.expr = expr;
        this.index = idx;
    }

    protected Type origType() {
        return expr.origType().baseType();
    }

    public ExprNode expr() {
        return expr;
    }

    public ExprNode index() {
        return index;
    }

    public Location location() {
        return expr.location();
    }

    protected void _dump(Dumper d) {
        if (type != null)
            d.printMember("type", type);
        d.printMember("expr", expr);
        d.printMember("index", index);
    }

    public <S, E> E accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }
}
