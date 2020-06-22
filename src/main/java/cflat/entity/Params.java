package cflat.entity;

import cflat.ast.Dumpable;
import cflat.ast.Dumper;
import cflat.ast.Location;
import cflat.type.ParamTypeRefs;
import cflat.type.TypeRef;
import java.util.*;

public class Params extends ParamSlots<Parameter> implements Dumpable {
    public Params(Location loc, List<Parameter> paramDescs) {
        super(loc, paramDescs, false);
    }

    public List<Parameter> parameters() {
        return paramDescriptors;
    }

    public ParamTypeRefs parametersTypeRef() {
        List<TypeRef> typerefs = new ArrayList<TypeRef>();
        for (Parameter param : paramDescriptors) {
            typerefs.add(param.typeNode().typeRef());
        }
        return new ParamTypeRefs(location, typerefs, vararg);
    }

    public void dump(Dumper d) {
        d.printNodeList("parameters", parameters());
    }
}
