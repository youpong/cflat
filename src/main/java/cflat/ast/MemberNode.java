package cflat.ast;

import cflat.type.Type;
import cflat.exception.SemanticError;

/**
 * Member 参照 st.memb
 */
public class MemberNode extends LHSNode {
    private ExprNode expr;
    private String member;
    
    public MemberNode(ExprNode expr, String member) {
	this.expr = expr;
	this.member = member;
    }

    /*
    public CompositeType baseType() {
	try {
	    return expr.type().getCompositeType();
	}
	catch(ClassCastException err) {
	    throw new SemanticError(err.getMessage());
	}
    }
    */
    // TODO: implement
    protected Type origType() {
	//return baseType().memberType(member);
	return null;
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
