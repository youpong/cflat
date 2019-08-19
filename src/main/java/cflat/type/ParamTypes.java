package cflat.type;

import cflat.entity.ParamSlots;
import cflat.ast.Location;
import java.util.*;

public class ParamTypes extends ParamSlots<Type> {
    protected ParamTypes(Location loc, List<Type> paramDescs, boolean vararg) {
	super(loc, paramDescs, vararg);
    }
}
