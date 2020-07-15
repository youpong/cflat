package cflat.ir;

import cflat.ast.Location;
import cflat.entity.ConstantTable;
import cflat.entity.DefinedFunction;
import cflat.entity.DefinedVariable;
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
    List<DefinedVariable> gvars; // cache
    List<DefinedVariable> comms; // cache

    public IR(Location source, List<DefinedVariable> defvars,
            List<DefinedFunction> defuns, List<UndefinedFunction> funcdecls,
            ToplevelScope scope, ConstantTable constantTable) {
        super();
        this.source = source;
        this.defvars = defvars;
        this.defuns = defuns;
        this.funcdecls = funcdecls;
        this.scope = scope;
        this.constantTable = constantTable;
    }

    public String fileName() {
        return source.sourceName();
    }

    // ...

    // 45
    public boolean isFunctionDefined() {
        return !defuns.isEmpty();
    }

    public List<DefinedFunction> definedFunctions() {
        return defuns;
    }

    // ...

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

    public boolean isGlobalVariableDefined() {
        return !definedGlobalVariables().isEmpty();
    }

    /**
     * Returns the list of global variables.
     * A global variable is a variable which has global scope and is initialized.
     */
    public List<DefinedVariable> definedGlobalVariables() {
        if (gvars == null) {
            initVariables();
        }
        return gvars;
    }

    public boolean isCommonSymbolDefined() {
        return !definedCommonSymbols().isEmpty();
    }

    /**
     * Returns the list of common symbols.
     * A common symbol is a variable which has global scope and is not initialized.
     */
    public List<DefinedVariable> definedCommonSymbols() {
        if (comms == null) {
            initVariables();
        }
        return comms;
    }

    // ...

    // 97
    private void initVariables() {
        gvars = new ArrayList<DefinedVariable>();
        comms = new ArrayList<DefinedVariable>();
        for (DefinedVariable var : scope.definedGlobalScopeVariables()) {
            (var.hasInitializer() ? gvars : comms).add(var);
        }
    }

    public boolean isStringLiteralDefined() {
        return !constantTable.isEmpty();
    }

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
