package cflat.ir;

import cflat.ast.Location;
import cflat.ir.Dumper;
import cflat.entity.DefinedVariable;
import cflat.entity.DefinedFunction;
import cflat.entity.UndefinedFunction;
import cflat.entity.ToplevelScope;
import cflat.entity.ConstantTable;
import java.io.*;
import java.util.*;

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
