package cflat.type;

public class ArrayType extends Type {
    protected Type baseType;
    protected long length;
    protected long pointerSize;
    static final protected long undefined = -1;
    
    public ArrayType(Type baseType, long pointerSize) {
	this(baseType, undefined, pointerSize);
    }
    public ArrayType(Type baseType, long length, long pointerSize) {
	this.baseType = baseType;
	this.length = length;
	this.pointerSize = pointerSize;
    }
}