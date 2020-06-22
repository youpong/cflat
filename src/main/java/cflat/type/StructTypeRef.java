package cflat.type;

import cflat.ast.Location;

public class StructTypeRef extends TypeRef {
    protected String name;

    public StructTypeRef(String name) {
        this(null, name);
    }

    public StructTypeRef(Location loc, String name) {
        super(loc);
        this.name = name;
    }

    public boolean equals(Object other) {
        if (!(other instanceof StructTypeRef))
            return false;
        return name.equals(((StructTypeRef) other).name);
    }

    public String toString() {
        return "struct " + name;
    }
}
