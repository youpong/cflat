package cflat.ast;

import cflat.type.Type;
import cflat.type.TypeRef;

/**
 * For expr operator "sizeof"
 */
public class SizeofExprNode extends ExprNode {
    protected ExprNode expr;
    protected TypeNode typeNode;
    
    public SizeofExprNode(ExprNode expr, TypeRef type) {
	this.expr = expr;
	this.typeNode = new TypeNode(type);
    }
    public ExprNode expr() {
	return expr;
    }
    public Type type() {
	return typeNode.type();
    }
    public TypeNode typeNode() {
	return typeNode;
    }
    public Location location() {
	return expr.location();
    }
    protected void _dump(Dumper d) {
	d.printMember("expr", expr);
    }
    public <S,E> E accept(ASTVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
}
