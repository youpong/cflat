package cflat.type;

import cflat.ast.Slot;
import cflat.ast.Location;
import java.util.*;

public class StructType extends CompositeType {
    public StructType(String name, List<Slot> membs, Location loc) {
	super(name, membs, loc);
    }
}
