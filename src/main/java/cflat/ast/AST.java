package cflat.ast;

import java.util.*;
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
}
