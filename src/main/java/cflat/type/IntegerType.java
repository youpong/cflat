package cflat.type;

public class IntegerType extends Type {
    protected long size;
    protected boolean isSigned;
    protected String name;
    
    public IntegerType(long size, boolean isSigned, String name) {
	super();
	this.size = size;
	this.isSigned = isSigned;
	this.name = name;
    }
}
