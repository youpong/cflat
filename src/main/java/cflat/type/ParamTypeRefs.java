package cflat.type;

import cflat.ast.Location;
import cflat.entity.ParamSlots;
import java.util.*;

public class ParamTypeRefs extends ParamSlots<TypeRef> {
    public ParamTypeRefs(List<TypeRef> paramDescs) {
	super(paramDescs);
    }
    public ParamTypeRefs(Location loc, List<TypeRef> paramDescs,
			 boolean vararg) {
	super(loc, paramDescs, vararg);
    }
}
