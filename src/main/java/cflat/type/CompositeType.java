package cflat.type;

import cflat.ast.Location;
import cflat.ast.Slot;
import java.util.*;
    
abstract public class CompositeType extends NamedType {
    protected List<Slot> membs;

    public CompositeType(String name, List<Slot> membs, Location loc) {
	super(name, loc);
	this.membs = membs;
    }
}
