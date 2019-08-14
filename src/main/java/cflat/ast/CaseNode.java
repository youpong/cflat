package cflat.ast;

import java.util.*;

// TODO: implement
public class CaseNode extends StmtNode {
    protected List<ExprNode> values;    
    protected BlockNode body;
    
    public CaseNode(Location loc, List<ExprNode> values, BlockNode body) {
	super(loc);
	this.values = values;
	this.body = body;
    }
    protected void _dump(Dumper d) {
	d.printNodeList("values", values);
	d.printMember("body", body);
    }
    public <S,E> S accept(ASTVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
}
