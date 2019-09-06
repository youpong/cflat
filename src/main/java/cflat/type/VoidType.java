package cflat.type;

public class VoidType extends Type {
    public VoidType() { }

    public boolean isVoid() { return true; }
    
    public long size() {
	return 1;
    }
    //
    public boolean isSameType(Type other) {
	return other.isVoid();
    }
    public boolean isCompatible(Type other) {
	return other.isVoid();
    }
    public boolean isCastableTo(Type other) {
	return other.isVoid();
    }
    //
}
