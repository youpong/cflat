package cflat.entity;

import cflat.ast.Location;
import java.util.*;

abstract public class ParamSlots<T> {
    protected Location location;
    protected List<T> paramDescriptors;
    protected boolean vararg;

    public ParamSlots(List<T> paramDescs) {
        this(null, paramDescs);
    }

    public ParamSlots(Location loc, List<T> paramDescs) {
        this(loc, paramDescs, false);
    }

    public ParamSlots(Location loc, List<T> paramDescs, boolean vararg) {
        super();
        this.location = loc;
        this.paramDescriptors = paramDescs;
        this.vararg = vararg;
    }

    public int argc() {
        if (vararg) {
            throw new Error("must not happen: Param#argc for vararg");
        }
        return paramDescriptors.size();
    }

    public int minArgc() {
        return paramDescriptors.size();
    }

    //
    public Location location() {
        return location;
    }

    public void acceptVarargs() {
        this.vararg = true;
    }
}
