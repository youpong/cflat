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

    public boolean isArray() {
        return true;
    }

    public boolean isAllocatedArray() {
        return length != undefined
                && (!baseType.isArray() || baseType.isAllocatedArray());
    }

    public boolean isIncompleteArray() {
        if (!baseType.isArray())
            return false;
        return !baseType.isAllocatedArray();
    }

    public Type baseType() {
        return baseType;
    }

    // Value size as pointer
    public long size() {
        return length;
    }

    public long allocSize() {
        if (length == undefined) {
            return size();
        } else {
            return baseType.allocSize() * length;
        }
    }

    //
    public boolean isSameType(Type other) {
        // length is not important
        if (!other.isPointer() && !other.isArray())
            return false;
        return baseType.isSameType(other.baseType());
    }

    public boolean isCompatible(Type target) {
        if (!target.isPointer() && !target.isArray())
            return false;
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
