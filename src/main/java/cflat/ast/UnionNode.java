package cflat.ast;

import cflat.type.TypeRef;
import cflat.type.Type;

import java.util.*;

public class UnionNode extends CompositeTypeDefinition {
    public UnionNode(Location loc, TypeRef ref,
		     String name, List<Slot> membs) {
	super(loc, ref, name, membs);
    }

    // TODO: implement
    public Type definitingType() {
	return null;
    }
    
    public <T> T accept(DeclarationVisitor<T> visitor) {
	return visitor.visit(this);
    }
}
