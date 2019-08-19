package cflat.type;

public class PointerType extends Type {
    protected long size;
    protected Type baseType;
    
    public PointerType(long size, Type baseType) {
	this.size = size;
	this.baseType = baseType;
    }
}
