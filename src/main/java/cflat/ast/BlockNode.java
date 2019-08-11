package cflat.ast;

import java.util.*;
import cflat.entity.DefinedVariable;

public class BlockNode extends StmtNode {
    protected List<DefinedVariable> variables;
    protected List<StmtNode> stmts;
    //protected LocalScope scope;

    public BlockNode(Location loc,
		     List<DefinedVariable> vars, List<StmtNode> stmts) {
	super(loc);
	this.variables = vars;
	this.stmts = stmts;
    }
    protected void _dump(Dumper d) {
	d.printNodeList("variables", variables);
	d.printNodeList("stmts", stmts);
    }
    public <S,E> S accept(ASTVisitor<S,E> visitor) {
	return visitor.visit(this);
    }
}
