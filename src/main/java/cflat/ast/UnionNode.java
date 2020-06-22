package cflat.ast;

import cflat.type.Type;
import cflat.type.TypeRef;
import java.util.*;

/**
 * 共用体定義
 */
public class UnionNode extends CompositeTypeDefinition {
    public UnionNode(Location loc, TypeRef ref, String name, List<Slot> membs) {
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
