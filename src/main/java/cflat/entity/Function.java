package cflat.entity;

import cflat.ast.TypeNode;

abstract public class Function extends Entity {
    
    public Function(boolean priv, TypeNode t, String name) {
	super(priv, t, name);
    }
}
