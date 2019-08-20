package cflat.compiler;

import cflat.ast.AST;
import cflat.ast.BlockNode;
import cflat.ast.CastNode;
import cflat.ast.Node;
import cflat.ast.CompositeTypeDefinition;
import cflat.ast.IntegerLiteralNode;
import cflat.ast.StringLiteralNode;
import cflat.ast.SizeofExprNode;
import cflat.ast.SizeofTypeNode;
import cflat.ast.TypeDefinition;
import cflat.ast.TypedefNode;
import cflat.ast.TypeNode;
import cflat.ast.VariableNode;
import cflat.ast.DeclarationVisitor;
import cflat.ast.UnionNode;
import cflat.ast.StructNode;
import cflat.ast.Slot;

import cflat.entity.Constant;
import cflat.entity.Entity;
import cflat.entity.EntityVisitor;
import cflat.entity.Function;
import cflat.entity.DefinedFunction;
import cflat.entity.DefinedVariable;
import cflat.entity.UndefinedFunction;
import cflat.entity.UndefinedVariable;
import cflat.entity.Parameter;

import cflat.type.CompositeType;
import cflat.type.Type;
import cflat.type.TypeTable;

import cflat.utils.ErrorHandler;
import java.util.*;

/**
 * 型名の解決
 * 型の名前(TypeRef) を型(Type) へ解決する。
 */
public class TypeResolver extends Visitor
    implements EntityVisitor<Void>, DeclarationVisitor<Void> {

    private TypeTable typeTable;
    private ErrorHandler errorHandler;
    
    // TODO: test
    public TypeResolver(TypeTable typeTable, ErrorHandler errorHandler) {
	this.typeTable = typeTable;
	this.errorHandler = errorHandler;
    }

    // TODO: test
    public void resolve(AST ast) {
	defineTypes(ast.types());
	for (TypeDefinition t : ast.types()) {
	    t.accept(this);
	}
	for (Entity e : ast.entities()) {
	    e.accept(this);
	}
    }
    // TODO: test
    private void defineTypes(List<TypeDefinition> deftypes) {
	for (TypeDefinition def : deftypes) {
	    if (typeTable.isDefined(def.typeRef())) {
		error(def, "duplicated type definitions: " + def.typeRef());
	    }
	    typeTable.put(def.typeRef(), def.definitingType());
	}
    }

    // TODO: test
    private void bindType(TypeNode n) {
	if (n.isResolved()) return;
	n.setType(typeTable.get(n.typeRef()));
    }

    //
    // Declarations
    //

    public Void visit(StructNode struct) {
	resolveCompositeType(struct);
	return null;
    }
    public Void visit(UnionNode union) {
	resolveCompositeType(union);
	return null;
    }
    // TODO: test    
    public void resolveCompositeType(CompositeTypeDefinition def) {
	CompositeType ct =
	    (CompositeType)typeTable.get(def.typeNode().typeRef());
	if (ct == null) {
	    throw new Error("cannot intern struct/union: " + def.name());
	}
	for (Slot s : ct.members()) {
	    bindType(s.typeNode());
	}
    }
    
    public Void visit(TypedefNode typedef) {
	bindType(typedef.typeNode());
	bindType(typedef.realTypeNode());
	return null;
    }

    //
    // Entities
    //
    
    public Void visit(DefinedVariable var) {
	bindType(var.typeNode());
	if (var.hasInitializer()) {
	    visitExpr(var.initializer());
	}
	return null;
    }
    public Void visit(UndefinedVariable var) {
	bindType(var.typeNode());
	return null;
    }

    // TODO: test
    public Void visit(Constant c) {
	bindType(c.typeNode());
	visitExpr(c.value());
	return null;
    }
    
    public Void visit(DefinedFunction func) {
	resolveFunctionHeader(func);
	visitStmt(func.body());
	return null;
    }
    public Void visit(UndefinedFunction func) {
	resolveFunctionHeader(func);
	return null;
    }
    private void resolveFunctionHeader(Function func) {
	bindType(func.typeNode());
	for (Parameter param : func.parameters()) {
	    Type t = typeTable.getParamType(param.typeNode().typeRef());
	    param.typeNode().setType(t);
	}
    }

    //
    // Expressions
    //

    public Void visit(BlockNode node) {
	for (DefinedVariable var : node.variables()) {
	    var.accept(this);
	}
	visitStmts(node.stmts());
	return null;
    }
    
    public Void visit(CastNode node) {
	bindType(node.typeNode());
	super.visit(node);
	return null;
    }

    public Void visit(SizeofExprNode node) {
	bindType(node.typeNode());
	super.visit(node);
	return null;
    }

    public Void visit(SizeofTypeNode node) {
	bindType(node.operandTypeNode());
	bindType(node.typeNode());
	super.visit(node);
	return null;
    }

    public Void visit(IntegerLiteralNode node) {
	bindType(node.typeNode());
	return null;
    }

    public Void visit(StringLiteralNode node) {
	bindType(node.typeNode());
	return null;
    }
    
    private void error(Node node, String msg) {
	errorHandler.error(node.location(), msg);
    }
}
