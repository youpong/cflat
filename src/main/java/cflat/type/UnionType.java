package cflat.type;

import cflat.ast.Location;
import cflat.ast.Slot;
import cflat.utils.AsmUtils;
import java.util.*;

public class UnionType extends CompositeType {
    public UnionType(String name, List<Slot> membs, Location loc) {
        super(name, membs, loc);
    }

    public boolean isUnion() {
        return true;
    }

    public boolean isSameType(Type other) {
        if (!other.isUnion())
            return false;
        return equals(other.getUnionType());
    }

    protected void computeOffsets() {
        long maxSize = 0;
        long maxAlign = 1;
        for (Slot s : members) {
            s.setOffset(0);
            maxSize = Math.max(maxSize, s.allocSize());
            maxAlign = Math.max(maxAlign, s.alignment());
        }
        cachedSize = AsmUtils.align(maxSize, maxAlign);
        cachedAlign = maxAlign;
    }
    // toString()
}
