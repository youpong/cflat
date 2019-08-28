package cflat.type;

import cflat.ast.Location;
import cflat.ast.Slot;
import cflat.exception.SemanticError;
import java.util.*;
import java.lang.reflect.*;
    
abstract public class CompositeType extends NamedType {
    protected List<Slot> members;
    protected long cachedSize;
    protected long cachedAlign;

    public CompositeType(String name, List<Slot> membs, Location loc) {
	super(name, loc);
	this.members = membs;
    }

    public boolean isCompatible(Type target) {
	return compareMemberTypes(target, "isCompatible");
    }
    public boolean isCastableTo(Type target) {
	return compareMemberTypes(target, "isCastableTo");
    }

    protected boolean compareMemberTypes(Type other, String cmpMethod) {
	if (isStruct() && !other.isStruct()) return false;
	if (isUnion() && !other.isUnion()) return false;
	CompositeType otherType = other.getCompositeType();
	if (members.size() != other.size()) return false;
	Iterator<Type> otherTypes = otherType.memberTypes().iterator();
	for (Type t : memberTypes()) {
	    if (! compareTypesBy(cmpMethod, t, otherTypes.next())) {
		return false;
	    }
	}
	return true;
    }
    protected boolean compareTypesBy(String cmpMethod, Type t, Type tt) {
	try {
	    Method cmp = Type.class.getMethod(cmpMethod,
					      new Class[] { Type.class });
	    Boolean b = (Boolean)cmp.invoke(t, new Object[] { tt });
	    return b.booleanValue();
	} catch (NoSuchMethodException ex) {
	    throw new Error(ex.getMessage());
	} catch (IllegalAccessException ex) {
	    throw new Error(ex.getMessage());
	} catch (InvocationTargetException ex) {
	    throw new Error(ex.getMessage());
	}
    }

    public long size() {
	if (cachedSize == Type.sizeUnknown) {
	    computeOffsets();
	}
	return cachedSize;
    }
    //
    public List<Slot> members() {
	return members;
    }
    public List<Type> memberTypes() {
	List<Type> result = new ArrayList<Type>();
	for (Slot s : members) {
	    result.add(s.type());
	}
	return result;
    }
    //
    public boolean hasMember(String name) {
	return (get(name) != null);
    }
    public Type memberType(String name) {
	return fetch(name).type();
    }
    //
    abstract protected void computeOffsets();
    
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
