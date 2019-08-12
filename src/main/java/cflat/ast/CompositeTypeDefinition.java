package cflat.ast;

import java.util.*;
import cflat.type.TypeRef;

abstract public class CompositeTypeDefinition extends TypeDefinition {
    protected List<Slot> members;
    
    public CompositeTypeDefinition(Location loc, TypeRef ref,
				   String name, List<Slot> membs) {
	super(loc, ref, name);
	this.members = membs;
    }

    protected void _dump(Dumper d) {
	d.printMember("name", name);
	d.printNodeList("members", members);
    }
}
