package cflat.ast;

import java.util.*;
import java.io.*;
import cflat.entity.DefinedVariable;
import cflat.entity.DefinedFunction;

public class AST extends Node {
    protected Location source;
    protected Declarations declarations;
    
    public AST(Location source, Declarations declarations) {
	super();
	this.source = source;
	this.declarations = declarations;
    }
    public Location location() {
	return source;
    }
    public List<DefinedVariable> definedVariables() {
	return declarations.defvars();
    }
    public List<DefinedFunction> definedFunctions() {
	return declarations.defuns();
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
