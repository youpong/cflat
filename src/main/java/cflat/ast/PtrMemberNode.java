package cflat.ast;

import cflat.type.Type;
import cflat.type.PointerType;
import cflat.type.CompositeType;
import cflat.exception.SemanticError;

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

    public CompositeType dereferedCompositeType() {
	try {
	    PointerType pt = expr().type().getPointerType();
	    return pt.baseType().getCompositeType();
	} catch (ClassCastException err) {
	    throw new SemanticError(err.getMessage());
	}
    }
    
    public Type dereferedType() {
	try {
	    PointerType pt = expr.type().getPointerType();
	    return pt.baseType();
	}
	catch(ClassCastException err) {
	    throw new SemanticError(err.getMessage());
	}
    }

    // TODO: test
    protected Type origType(){
	return dereferedCompositeType().memberType(member);
    }
    public ExprNode expr() {
	return expr;
    }

    public String member() {
	return member;
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
