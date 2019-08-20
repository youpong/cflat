package cflat.ast;

import cflat.type.Type;
import cflat.type.TypeRef;

/**
 * For type operator "sizeof"
 */
public class SizeofTypeNode extends ExprNode {
    protected TypeNode operand;
    protected TypeNode typeNode;
    
    public SizeofTypeNode(TypeNode operand, TypeRef type) {
	this.operand = operand;
	this.typeNode = new TypeNode(type);
    }

    public Type type() {
	return typeNode.type();
    }
    public TypeNode operandTypeNode() {
	return operand;
    }
    public TypeNode typeNode() {
	return typeNode;
    }
    public Location location() {
	return operand.location();
    }
    // TODO: test
    protected void _dump(Dumper d) {
	d.printMember("operand", typeNode);
    }
    public <S,E> E accept(ASTVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
}

