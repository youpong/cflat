package cflat.ir;

import cflat.ast.Location;
import cflat.ir.Dumper;
import cflat.entity.ConstantTable;
import cflat.entity.DefinedVariable;
import cflat.entity.DefinedFunction;
import cflat.entity.Function;
import cflat.entity.ToplevelScope;
import cflat.entity.UndefinedFunction;
import cflat.entity.Variable;
import java.io.*;
import java.util.*;

/**
 * 中間表現のルート
 */
public class IR {
    Location source;
    List<DefinedVariable> defvars;
    List<DefinedFunction> defuns;
    List<UndefinedFunction> funcdecls;
    ToplevelScope scope;
    ConstantTable constantTable;

    public IR(Location source,
	      List<DefinedVariable> defvars,
	      List<DefinedFunction> defuns,
	      List<UndefinedFunction> funcdecls,
	      ToplevelScope scope,
	      ConstantTable constantTable) {
	super();
	this.source = source;
	this.defvars = defvars;
	this.defuns = defuns;
	this.funcdecls = funcdecls;
	this.scope = scope;
	this.constantTable = constantTable;
    }
    
    //...
    
    // 57
    public List<Function> allFunctions() {
	List<Function> result = new ArrayList<Function>();
	result.addAll(defuns);
	result.addAll(funcdecls);
	return result;
    }

    /** a list of all defined/declared global-scope variables */
    public List<Variable> allGlobalVariables() {
	return scope.allGlobalVariables();
    }
    
    // ...

    // 109
    public ConstantTable constantTable() {
	return constantTable;
    }

    public void dump() {
	dump(System.out);
    }
    
    public void dump(PrintStream s) {
	Dumper d = new Dumper(s);
	d.printClass(this, source);
	d.printVars("variables", defvars);
	d.printFuncs("functions", defuns);
    }
}
