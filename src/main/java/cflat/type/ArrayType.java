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
    //
    
    // Value size as pointer
    public long size() {
	return length;
    }
    //
    public boolean isSameType(Type other) {
	// length is not important
	if (!other.isPointer() && !other.isArray()) return false;
	return baseType.isSameType(other.baseType());
    }
    public boolean isCompatible(Type target) {
	if (!target.isPointer() && !target.isArray()) return false;
	if (target.baseType().isVoid()) {
	    return true;
	}
	return baseType.isCompatible(target.baseType())
	    && baseType.size() == target.baseType().size();
    }
    public boolean isCastableTo(Type target) {
	return target.isPointer() || target.isArray();
    }
}
