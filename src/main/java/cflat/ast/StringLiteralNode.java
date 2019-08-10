package cflat.ast;

import cflat.type.TypeRef;

// TODO: implement
public class StringLiteralNode extends LiteralNode {
    protected String value;

    public StringLiteralNode(Location loc, TypeRef ref, String value) {
	super(loc, ref);
	this.value = value;
    }
    
    protected void _dump(Dumper d) {
	d.printMember("value", value);
    }

    public <S,E> E accept(ASTVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
}
