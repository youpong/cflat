package cflat.type;

import cflat.ast.Location;
import cflat.ast.TypeNode;

public class UserType extends NamedType {
    protected TypeNode real;
    
    public UserType(String name, TypeNode real, Location loc) {
	super(name, loc);
	this.real = real;
    }
}
