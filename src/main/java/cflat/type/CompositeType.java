package cflat.type;

import cflat.ast.Location;
import cflat.ast.Slot;
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
}
