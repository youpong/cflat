package cflat.ast;

import cflat.type.TypeRef;
import java.util.*;

public class StructNode extends CompositeTypeDefinition {
    public StructNode(Location loc, TypeRef ref,
		      String name, List<Slot> membs) {
	super(loc, ref, name, membs);
    }
    /*
    public <T> T accept(DeclarationVisitor<T> visitor) {
	return visitor.visit(this);
    }
    */
}
