package cflat.entity;

import cflat.exception.SemanticException;
import java.util.*;

// TODO: implement
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
    public Entity get(String name) throws SemanticException {
	DefinedVariable var = variables.get(name);
	if (var != null)
	    return var;
	return parent.get(name);
    }
}
