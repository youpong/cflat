package cflat.entity;

import java.util.*;
import cflat.utils.ErrorHandler;
import cflat.exception.SemanticException;

// TODO: implement
/**
 * プログラムのトップレベルスコープを表現する。関数とグローバル変数を持つ。
 */
public class ToplevelScope extends Scope {
    protected Map<String, Entity> entities;
    protected List<DefinedVariable> staticLocalVariables;

    public ToplevelScope() {
	super();
	entities = new LinkedHashMap<String, Entity>();
	staticLocalVariables = null;
    }
    
    // TODO: test
    public void declareEntity(Entity entity) throws SemanticException {
	Entity e = entities.get(entity.name());
	if (e != null) {
	    throw new SemanticException("duplicated declaration: " +
					entity.name() + ": " + e.location() +
					" and " + entity.location());
	}
	entities.put(entity.name(), entity);
    }
    
    // TODO: test
    public void defineEntity(Entity entity) throws SemanticException {
	Entity e = entities.get(entity.name());
	if (e != null && e.isDefined()) {
	    throw new SemanticException("duplicated definition: " +
					entity.name() + ": " + e.location() +
					" and " + entity.location());
	}
	entities.put(entity.name(), entity);
    }
    
    public Entity get(String name) throws SemanticException {
	Entity ent = entities.get(name);
	if (ent == null)
	    throw new SemanticException("unresolved reference: " + name);
	return ent;
    }
    // TODO: implement
    public void checkReferences(ErrorHandler h) {
	for (Entity ent : entities.values()) {
	    if (ent.isDefined()
		&& ent.isPrivate()
		&& !ent.isConstant()
		&& !ent.isRefered()) {
		h.warn(ent.location(), "unused variable: " + ent.name());
	    }
	}
	// do not check parameters
	for (LocalScope funcScope : children) {
	    for (LocalScope s : funcScope.children) {
		s.checkReferences(h);
	    }
	}
    }

    // ...
    
    // 66
    /** Returns a list of all global variables
     * "All global variable" means:
     *
     *    * has global scope
     *    * defined or undefined
     *    * public or private
     */
    public List<Variable> allGlobalVariables() {
	List<Variable> result = new ArrayList<Variable>();
	for (Entity ent : entities.values()) {
	    if (ent instanceof Variable) {
		result.add((Variable)ent);
	    }
	}
	result.addAll(staticLocalVariables());
	return result;
    }
    
    //...

    public List<DefinedVariable> staticLocalVariables() {
	if (staticLocalVariables == null) {
	    staticLocalVariables = new ArrayList<DefinedVariable>();
	    for (LocalScope s : children) {
		staticLocalVariables.addAll(s.staticLocalVariables());
	    }
	    Map<String, Integer> seqTable = new HashMap<String, Integer>();
	    for (DefinedVariable var : staticLocalVariables) {
		Integer seq = seqTable.get(var.name());
		if (seq == null) {
		    var.setSequence(0);
		    seqTable.put(var.name(), 1);
		} else {
		    var.setSequence(seq);
		    seqTable.put(var.name(), seq + 1);
		}
	    }
	}
	return staticLocalVariables;
    }

    // ...
}
