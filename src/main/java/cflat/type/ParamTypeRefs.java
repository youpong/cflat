package cflat.type;

import cflat.ast.Location;
import cflat.entity.ParamSlots;
import java.util.*;

public class ParamTypeRefs extends ParamSlots<TypeRef> {
    public ParamTypeRefs(List<TypeRef> paramDescs) {
        super(paramDescs);
    }

    public ParamTypeRefs(Location loc, List<TypeRef> paramDescs, boolean vararg) {
        super(loc, paramDescs, vararg);
    }

    public List<TypeRef> typerefs() {
        return paramDescriptors;
    }

    // TODO: test20
    public ParamTypes internTypes(TypeTable table) {
        List<Type> types = new ArrayList<Type>();
        for (TypeRef ref : paramDescriptors) {
            types.add(table.getParamType(ref));
        }
        return new ParamTypes(location, types, vararg);
    }
}
