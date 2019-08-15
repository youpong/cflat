package cflat.ast;

import cflat.type.Type;

/**
 * ポインタ間接メンバ参照 ptr-&gt;memb
 */
public class PtrMemberNode extends LHSNode {
    // TODO: private?
    public ExprNode expr;
    public String member;

    public PtrMemberNode(ExprNode expr, String member) {
	this.expr = expr;
	this.member = member;
    }
    /*
    public Type dereferencedType() {
	try {
	    PointerType pt = expr.type().getPointerType();
	    return pt.baseType();
	}
	catch(ClassCastException err) {
	    throw new SemanticError(err.getMessage());
	}
    }
    */
    // TODO: implement
    protected Type origType(){
	//return dereferencedCompositeType().memberType(member);
	return null;
    }
    public ExprNode expr() {
	return expr;
    }
    public Location location() {
	return expr.location();
    }
    protected void _dump(Dumper d) {
	if(type!=null)
	    d.printMember("type", type);
	d.printMember("expr", expr);
	d.printMember("member", member);
    }
    public <S,E> E accept(ASTVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
}
