package cflat.ast;

import cflat.type.Type;

// TODO: implement
/**
 * unary operator "+", "-", "!", "~"
 */
public class UnaryOpNode extends ExprNode {
    protected String operator;
    protected ExprNode expr;

    public UnaryOpNode(String op, ExprNode expr) {
        this.operator = op;
        this.expr = expr;
    }

    public String operator() {
        return operator;
    }

    public Type type() {
        return expr.type();
    }

    public ExprNode expr() {
        return expr;
    }

    public Location location() {
        return expr.location();
    }

    public void _dump(Dumper d) {
        d.printMember("operator", operator);
        d.printMember("expr", expr);
    }

    public <S, E> E accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }
}
