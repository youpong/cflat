package cflat.ast;

// TODO: test
public class OpAssignNode extends AbstractAssignNode {
    protected String operator;

    public OpAssignNode(ExprNode lhs, String operator, ExprNode rhs) {
        super(lhs, rhs);
        this.operator = operator;
    }

    public String operator() {
        return operator();
    }

    public <S, E> E accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }
}
