package cflat.type;

public class FunctionTypeRef extends TypeRef {
    protected TypeRef returnType;
    protected ParamTypeRefs params;

    public FunctionTypeRef(TypeRef returnType, ParamTypeRefs params) {
	super(returnType.location());
	this.returnType = returnType;
	this.params = params;
    }
}
