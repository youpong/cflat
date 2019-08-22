package cflat.compiler;

import cflat.ast.AST;
import cflat.ast.Node;
import cflat.ast.BlockNode;
import cflat.ast.DereferenceNode;
import cflat.ast.AddressNode;
import cflat.ast.ExprNode;
import cflat.ast.StmtNode;
import cflat.ast.LHSNode;
import cflat.ast.Location;
import cflat.type.Type;
import cflat.type.TypeTable;
import cflat.utils.ErrorHandler;
import cflat.exception.SemanticError;
import cflat.exception.SemanticException;
import cflat.entity.DefinedVariable;
import cflat.entity.DefinedFunction;

/**
 * 式の妥当性のチェック
 */
class DereferenceChecker extends Visitor
{
    private final TypeTable typeTable;
    private final ErrorHandler errorHandler;
    
    public DereferenceChecker(TypeTable types, ErrorHandler errorHandler) {
	this.typeTable = types;
	this.errorHandler = errorHandler;
    }
    
    public void check(AST ast) throws SemanticException {
	for (DefinedVariable var : ast.definedVariables()) {
	    checkToplevelVariable(var);
	}
	for (DefinedFunction f : ast.definedFunctions()) {
	    check(f.body());
	}
	if (errorHandler.errorOccured()) {
	    throw new SemanticException("compile failed.");
	}
    }

    // TODO: implement
    private void checkToplevelVariable(DefinedVariable var) {
    }

    private void check(StmtNode node) {
	node.accept(this);
    }
    private void check(ExprNode node) {
	node.accept(this);
    }

    //
    // Statements
    //
    
    // TODO: implement
    public Void visit(BlockNode node) {
	for (DefinedVariable var : node.variables()) {
	    checkVariable(var);
	}
	for (StmtNode stmt : node.stmts()) {
	    try {
		check(stmt);
	    } catch (SemanticError error) {
		;
	    }
	}
	return null;
    }

    private void checkVariable(DefinedVariable var) {
	if (var.hasInitializer()) {
	    try {
		check(var.initializer());
	    } catch (SemanticError err) {
		;
	    }
	}
    }
    
    public Void visit(DereferenceNode node) {
	super.visit(node);
	if (! node.expr().isPointer()) {
	    undereferableError(node.location());
	}
	handleImplicitAddress(node);
	return null;
    }

    public Void visit(AddressNode node) {
	super.visit(node);
	if (! node.expr().isLvalue()) {
	    semanticError(node.location(), "invalid expression for &");
	}
	Type base = node.expr().type();
	if (! node.expr().isLoadable()) {
	    node.setType(base);
	} else {
	    node.setType(typeTable.pointerTo(base));
	}
	return null;
    }

    //
    // Utilities
    //

    private void handleImplicitAddress(LHSNode node) {
	if (! node.isLoadable()) {
	    Type t = node.type();
	    if (t.isArray()) {
		node.setType(typeTable.pointerTo(t.baseType()));
	    } else {
		node.setType(typeTable.pointerTo(t));
	    }
	}
    }
    private void undereferableError(Location loc) {
	semanticError(loc, "dereferencing non-pointer expression");
    }
    private void semanticError(Node n, String msg) {    
	semanticError(n.location(), msg);
    }
    private void semanticError(Location loc, String msg) {
	errorHandler.error(loc, msg);
	throw new SemanticError("invalid expr");
    }
}
