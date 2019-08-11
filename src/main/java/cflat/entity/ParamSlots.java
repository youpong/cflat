package cflat.entity;

import java.util.*;
import cflat.ast.Location;

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
    public Location location() {
	return location;
    }
    public void acceptVarargs() {
	this.vararg = true;
    }
}
