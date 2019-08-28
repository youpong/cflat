package cflat.type;

import cflat.entity.ParamSlots;
import cflat.ast.Location;
import java.util.*;

public class ParamTypes extends ParamSlots<Type> {
    protected ParamTypes(Location loc, List<Type> paramDescs, boolean vararg) {
	super(loc, paramDescs, vararg);
    }
    public List<Type> types() {
	return paramDescriptors;
    }
    public boolean isSameType(ParamTypes other) {
	if (vararg != other.vararg) return false;
	if (minArgc() != other.minArgc()) return false;
	Iterator<Type> otherTypes = other.types().iterator();
	for (Type t : paramDescriptors) {
	    if (! t.isSameType(otherTypes.next())) {
		return false;
	    }
	}
	return true;
    }
}
