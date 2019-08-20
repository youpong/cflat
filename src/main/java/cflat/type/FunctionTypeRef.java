package cflat.type;

public class FunctionTypeRef extends TypeRef {
    protected TypeRef returnType;
    protected ParamTypeRefs params;

    public FunctionTypeRef(TypeRef returnType, ParamTypeRefs params) {
	super(returnType.location());
	this.returnType = returnType;
	this.params = params;
    }
    public TypeRef returnType() {
	return returnType;
    }
    public ParamTypeRefs params() {
	return params;
    }
    public boolean equals(Object other) {
	return (other instanceof FunctionTypeRef)
	    && equals((FunctionTypeRef)other);
    }
    public boolean equals(FunctionTypeRef other) {
	return returnType.equals(other.returnType()) &&
	    params.equals(other.params());
    }
    public String toString() {
	StringBuffer buf = new StringBuffer();
	buf.append(returnType.toString());
	buf.append(" (");
	String sep = "";
	for (TypeRef ref : params.typerefs()) {
	    buf.append(sep);
	    buf.append(ref.toString());
	    sep = ", ";
	}
	buf.append(")");
	return buf.toString();
    }
}
