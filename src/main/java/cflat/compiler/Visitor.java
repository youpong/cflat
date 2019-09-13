package cflat.compiler;

import cflat.ast.*;
import cflat.entity.DefinedVariable;
import java.util.*;

abstract public class Visitor implements ASTVisitor<Void, Void> {
    public Visitor() { }

    protected void visitStmt(StmtNode stmt) {
	stmt.accept(this);
    }
    protected void visitStmts(List<? extends StmtNode> stmts) {
	for (StmtNode s : stmts) {
	    visitStmt(s);
	}
    }
    protected void visitExpr(ExprNode expr) {
	expr.accept(this);
    }
    protected void visitExprs(List<? extends ExprNode> exprs) {
	for (ExprNode e : exprs) {
	    visitExpr(e);
	}
    }

    //
    // Stmt
    //

    public Void visit(BlockNode node) {
	for (DefinedVariable var : node.variables()) {
	    if (var.hasInitializer()) {
		visitExpr(var.initializer());
	    }
	}
	visitStmts(node.stmts());
	return null;
    }
    public Void visit(ExprStmtNode node) {
	visitExpr(node.expr());
	return null;
    }
    public Void visit(IfNode node) {
	visitExpr(node.cond());
	visitStmt(node.thenBody());
	if (node.elseBody() != null) {
	    visitStmt(node.elseBody());
	}
	return null;
    }
    public Void visit(SwitchNode node) {
	visitExpr(node.cond());
	visitStmts(node.cases());
	return null;
    }
    public Void visit(CaseNode node) {
	visitExprs(node.values());
	visitStmt(node.body());
	return null;
    }
    public Void visit(WhileNode node) {
	visitExpr(node.cond());
	visitStmt(node.body());
	return null;
    }
    public Void visit(DoWhileNode node) {
	visitStmt(node.body());	
	visitExpr(node.cond());
	return null;
    }
    public Void visit(ForNode node) {
	visitStmt(node.init());	
	visitExpr(node.cond());
	visitStmt(node.incr());		
	visitStmt(node.body());
	return null;
    }
    public Void visit(BreakNode node) {	return null; }
    public Void visit(ContinueNode node) { return null; }
    public Void visit(GotoNode node) { return null; }
    public Void visit(LabelNode node) {
	visitStmt(node.stmt());
	return null;
    }
    public Void visit(ReturnNode node) {
	if (node.expr() != null) {
	    visitExpr(node.expr());
	}
	return null;
    }
    
    //
    // Expressions
    //

    public Void visit(CondExprNode node) {
	visitExpr(node.cond());
	visitExpr(node.thenExpr());
	if (node.elseExpr() != null) {
	    visitExpr(node.elseExpr());
	}
	return null;
    }
    public Void visit(LogicalOrNode node) {
	visitExpr(node.left());	
	visitExpr(node.right());
	return null;
    }
    public Void visit(LogicalAndNode node) {
	visitExpr(node.left());	
	visitExpr(node.right());
	return null;
    }
    public Void visit(AssignNode node) {
	visitExpr(node.lhs());
	visitExpr(node.rhs());
	return null;
    }
    public Void visit(OpAssignNode node) {
	visitExpr(node.lhs());
	visitExpr(node.rhs());	
	return null;
    }
    public Void visit(BinaryOpNode node) {
	visitExpr(node.left());
	visitExpr(node.right());
	return null;
    }
    public Void visit(UnaryOpNode node) {
	visitExpr(node.expr());
	return null;
    }
    public Void visit(PrefixOpNode node) {
	visitExpr(node.expr());
	return null;
    }
    public Void visit(SuffixOpNode node) {
	visitExpr(node.expr());
	return null;
    }
    public Void visit(FuncallNode node) {
	visitExpr(node.expr());
	visitExprs(node.args());		
	return null;
    }
    public Void visit(ArefNode node) {
	visitExpr(node.expr());
	visitExpr(node.index());
	return null;
    }
    public Void visit(MemberNode node) {
	visitExpr(node.expr());
	return null;
    }
    public Void visit(PtrMemberNode node) {
	visitExpr(node.expr());
	return null;
    }
    public Void visit(DereferenceNode node) {
	visitExpr(node.expr());
	return null;
    }
    public Void visit(AddressNode node) {
	visitExpr(node.expr());
	return null;
    }
    public Void visit(CastNode node) {
	visitExpr(node.expr());
	return null;
    }
    public Void visit(SizeofExprNode node) {
	visitExpr(node.expr());
	return null;
    }
    public Void visit(SizeofTypeNode node) {
	return null;
    }
    public Void visit(VariableNode node) { return null; }
    public Void visit(IntegerLiteralNode node) { return null; }
    public Void visit(StringLiteralNode node) {	return null; }
}
