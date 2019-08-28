package cflat.entity;

import cflat.ast.TypeNode;
import cflat.type.Type;
import java.util.*;

abstract public class Function extends Entity {
    
    public Function(boolean priv, TypeNode t, String name) {
	super(priv, t, name);
    }

    abstract public List<Parameter> parameters();

    public Type returnType() {
	return type().getFunctionType().returnType();
    }
}
