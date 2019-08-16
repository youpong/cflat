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
	if (e != null) {
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
    }
}
