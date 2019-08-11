package cflat.type;

public class ArrayTypeRef extends TypeRef {
    protected TypeRef baseType;
    protected long length;
    static final protected long undefined = -1;
    
    public ArrayTypeRef(TypeRef baseType) {
	super(baseType.location());
	this.baseType = baseType;
	this.length = undefined;
    }

    public ArrayTypeRef(TypeRef baseType, long length) {
	super(baseType.location());
	if (length < 0) throw new Error("negative array length");
	this.baseType = baseType;
	this.length = length;
    }
}
