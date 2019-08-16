package cflat.compiler;

import cflat.ast.AST;
import cflat.ast.BlockNode;
import cflat.ast.ExprNode;
import cflat.ast.StmtNode;
import cflat.ast.VariableNode;
import cflat.ast.Node;
import cflat.ast.Location;
import cflat.entity.Constant;
import cflat.entity.Entity;
import cflat.entity.Scope;
import cflat.entity.ConstantTable;
import cflat.entity.DefinedFunction;
import cflat.entity.DefinedVariable;
import cflat.entity.LocalScope;
import cflat.entity.ToplevelScope;
import cflat.exception.SemanticException;
import cflat.utils.ErrorHandler;

import java.util.*;

// TODO: test
/**
 * 変数参照の解決
 */
public class LocalResolver extends Visitor {
    private final LinkedList<Scope> scopeStack;
    private final ConstantTable constantTable;
    private final ErrorHandler errorHandler;

    public LocalResolver(ErrorHandler h) {
	this.errorHandler = h;
	this.scopeStack = new LinkedList<Scope>();
	this.constantTable = new ConstantTable();
    }

    private void resolve(StmtNode n) { n.accept(this); }
    private void resolve(ExprNode n) { n.accept(this); }    
    
    public void resolve(AST ast) throws SemanticException {
	ToplevelScope toplevel = new ToplevelScope();
	scopeStack.add(toplevel);

	for(Entity decl : ast.declarations()) {
	    toplevel.declareEntity(decl);
	}
	for (Entity ent : ast.definitions()) {
	    toplevel.defineEntity(ent);
	}
	resolveGvarInitializers(ast.definedVariables());
	resolveConstantValues(ast.constants());
	resolveFunctions(ast.definedFunctions());
	toplevel.checkReferences(errorHandler);
	if(errorHandler.errorOccured()) {
	    throw new SemanticException("compile failed");
	}

	ast.setScope(toplevel);
	ast.setConstantTable(constantTable);
    }
    // TODO: test
    private void resolveGvarInitializers(List<DefinedVariable> gvars) {
	for (DefinedVariable gvar : gvars) {
	    if (gvar.hasInitializer()) {
		resolve(gvar.initializer());
	    }
	}
    }
    // TODO: test
    private void resolveConstantValues(List<Constant> consts) {
	for (Constant c : consts) {
	    resolve(c.value());
	}
    }
    // TODO: test
    private void resolveFunctions(List<DefinedFunction> funcs){
	for (DefinedFunction func : funcs) {
	    pushScope(func.parameters());
	    resolve(func.body()); 
	    func.setScope(popScope());
	}
    }
    
    // TODO: test
    private void pushScope(List<? extends DefinedVariable> vars) {
	LocalScope scope = new LocalScope(currentScope());
	for (DefinedVariable var : vars) {
	    if (scope.isDefinedLocally(var.name())) {
		error(var.location(), "duplicated variable in scope: " +
		      var.name());
	    } else {
		scope.defineVariable(var);
	    }
	}
	scopeStack.addLast(scope);
    }

    private LocalScope popScope() {
	return (LocalScope)scopeStack.removeLast();
    }
    private Scope currentScope() {
	return scopeStack.getLast();
    }
    /*
    public Void visit(StringLiteralNode node) {
	node.setEntry(constantTable.intern(node.value()));
	return null;
	}
    */

    public Void visit(BlockNode node) {
	pushScope(node.variables());
	super.visit(node); // TODO: 振る舞いを理解する
	node.setScope(popScope());
	return null;
    }
    // TODO: test
    public Void visit(VariableNode node) {
	try {
	    Entity ent = currentScope().get(node.name());
	    ent.refered();
	    node.setEntity(ent);
	} catch(SemanticException ex) {
	    error(node, ex.getMessage());
	}
	return null;
    }

    private void error(Node node, String message) {
	errorHandler.error(node.location(), message);
    }
    private void error(Location loc, String message) {
	errorHandler.error(loc, message);
    }
}
