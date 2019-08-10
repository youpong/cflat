package cflat.ast;

import cflat.type.Type;
import cflat.type.TypeRef;

public class SizeofTypeNode extends ExprNode {
    protected TypeNode operand;
    protected TypeNode type;
    
    public SizeofTypeNode(TypeNode operand, TypeRef type) {
	this.operand = operand;
	this.type = new TypeNode(type);
    }

    public Type type() {
	return type.type();
    }
    public Location location() {
	return operand.location();
    }
    // TODO: test
    protected void _dump(Dumper d) {
	d.printMember("operand", type);
    }
    public <S,E> E accept(ASTVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
}

