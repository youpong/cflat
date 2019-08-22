package cflat.type;

import cflat.ast.Location;
import cflat.ast.Slot;
import cflat.exception.SemanticError;
import java.util.*;
    
abstract public class CompositeType extends NamedType {
    protected List<Slot> members;

    public CompositeType(String name, List<Slot> membs, Location loc) {
	super(name, loc);
	this.members = membs;
    }

    public List<Slot> members() {
	return members;
    }
    //
    public boolean hasMember(String name) {
	return (get(name) != null);
    }
    public Type memberType(String name) {
	return fetch(name).type();
    }
    //
    protected Slot fetch(String name) {
	Slot s = get(name);
	if (s == null) {
	    throw new SemanticError("no such member in " +
				    toString() + ": " + name);
	}
	return s;
    }
    
    public Slot get(String name) {
	for (Slot s : members) {
	    if (s.name().equals(name)) {
		return s;
	    }
	}
	return null;
    }
}
