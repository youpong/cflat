package cflat.ast;

import java.util.*;
import java.io.*;
import cflat.entity.Constant;
import cflat.entity.Entity;
import cflat.entity.DefinedVariable;
import cflat.entity.DefinedFunction;
import cflat.entity.ToplevelScope;
import cflat.entity.ConstantTable;


public class AST extends Node {
    protected Location source;
    protected Declarations declarations;
    protected ToplevelScope scope;
    protected ConstantTable constantTable;
    
    public AST(Location source, Declarations declarations) {
	super();
	this.source = source;
	this.declarations = declarations;
    }
    public Location location() {
	return source;
    }
    public List<Entity> declarations() {
	List<Entity> result = new ArrayList<Entity>();
	result.addAll(declarations.funcdecls);
	result.addAll(declarations.vardecls);
	return result;
    }
    public List<Entity> definitions() {
	List<Entity> result = new ArrayList<Entity>();
	result.addAll(declarations.defvars);
	result.addAll(declarations.defuns);
	result.addAll(declarations.constants);
	return result;
    }
    public List<Constant> constants() {
	return declarations.constants();
    }
    public List<DefinedVariable> definedVariables() {
	return declarations.defvars();
    }
    public List<DefinedFunction> definedFunctions() {
	return declarations.defuns();
    }
    public void setScope(ToplevelScope scope) {
	if (this.scope != null) {
	    throw new Error("must not happen: ToplevelScope set twice");
	}
	this.scope = scope;
    }
    public void setConstantTable(ConstantTable table) {
	if (this.constantTable != null) {
	    throw new Error("must not happen: ConstantTable set twice");
	}
	this.constantTable = table;
    }
    protected void _dump(Dumper d) {
	d.printNodeList("variables", definedVariables());
	d.printNodeList("functions", definedFunctions());
    }
    public void dumpTokens(PrintStream s) {
	for (CflatToken t : source.token()) {
	    printPair(t.kindName(), t.dumpedImage(), s);
	}
    }
    
    static final private int NUM_LEFT_COLUMNS = 24;
    
    private void printPair(String key, String value, PrintStream s) {
	s.print(key);
	for (int n = NUM_LEFT_COLUMNS - key.length(); n > 0; n--) 
	    s.print(" ");
	s.println(value);
    }
}
