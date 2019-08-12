package cflat.ast;

import cflat.type.TypeRef;

import java.util.*;

public class UnionNode extends CompositeTypeDefinition {
    public UnionNode(Location loc, TypeRef ref,
		     String name, List<Slot> membs) {
	super(loc, ref, name, membs);
    }
}
