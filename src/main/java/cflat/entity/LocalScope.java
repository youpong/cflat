package cflat.entity;

import cflat.exception.SemanticException;
import cflat.type.Type;
import cflat.utils.ErrorHandler;
import java.util.*;

// TODO: implement
/**
 * ローカル変数のスコープ1つを表現する。仮引数またはローカル変数を持つ。
 */
public class LocalScope extends Scope {
    protected Scope parent;
    protected Map<String, DefinedVariable> variables;

    public LocalScope(Scope parent) {
        super();
        this.parent = parent;
        parent.addChild(this);
        variables = new LinkedHashMap<String, DefinedVariable>();
    }

    public boolean isDefinedLocally(String name) {
        return variables.containsKey(name);
    }

    public void defineVariable(DefinedVariable var) {
        if (variables.containsKey(var.name())) {
            throw new Error("duplicated variable: " + var.name());
        }
        variables.put(var.name(), var);
    }

    public DefinedVariable allocateTmp(Type t) {
        DefinedVariable var = DefinedVariable.tmp(t);
        defineVariable(var);
        return var;
    }

    public Entity get(String name) throws SemanticException {
        DefinedVariable var = variables.get(name);
        if (var != null)
            return var;
        return parent.get(name);
    }

    // ...

    /**
     * Returns all static local variables defined in this scope.
     */
    public List<DefinedVariable> staticLocalVariables() {
        List<DefinedVariable> result = new ArrayList<DefinedVariable>();
        for (LocalScope s : allLocalScopes()) {
            for (DefinedVariable var : s.variables.values()) {
                if (var.isPrivate()) {
                    result.add(var);
                }
            }
        }
        return result;
    }

    /**
     * Returns a list of all child scopes including this scope.
     */
    protected List<LocalScope> allLocalScopes() {
        List<LocalScope> result = new ArrayList<LocalScope>();
        collectScope(result);
        return result;
    }

    protected void collectScope(List<LocalScope> buf) {
        buf.add(this);
        for (LocalScope s : children) {
            s.collectScope(buf);
        }
    }

    public void checkReferences(ErrorHandler h) {
        for (DefinedVariable var : variables.values()) {
            if (!var.isRefered()) {
                h.warn(var.location(), "unused variable: " + var.name());
            }
        }
        for (LocalScope c : children) {
            c.checkReferences(h);
        }
    }
}
