package cflat.ast;

import cflat.type.StructType;
import cflat.type.Type;
import cflat.type.TypeRef;
import java.util.*;

/**
 * 構造体定義
 */
public class StructNode extends CompositeTypeDefinition {
    public StructNode(Location loc, TypeRef ref, String name, List<Slot> membs) {
        super(loc, ref, name, membs);
    }

    // TODO: implement
    public Type definitingType() {
        return new StructType(name(), members(), location());
    }

    public <T> T accept(DeclarationVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
