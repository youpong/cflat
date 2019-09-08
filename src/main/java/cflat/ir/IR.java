package cflat.ir;

import cflat.ast.Location;
import cflat.ir.Dumper;
import cflat.entity.DefinedVariable;
import cflat.entity.DefinedFunction;
import java.io.*;
import java.util.*;

public class IR {
    Location source;
    List<DefinedVariable> defvars;
    List<DefinedFunction> defuns;

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
