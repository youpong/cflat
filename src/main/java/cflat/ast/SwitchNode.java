package cflat.ast;

import java.util.*;

public class SwitchNode extends StmtNode {
    protected ExprNode cond;
    protected List<CaseNode> cases;
    public SwitchNode(Location loc, ExprNode cond, List<CaseNode> cases) {
	super(loc);
	this.cond = cond;
	this.cases = cases;
    }
    protected void _dump(Dumper d) {
	d.printMember("cond", cond);
	d.printNodeList("cases", cases);
    }
    public <S,E> S accept(ASTVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
}