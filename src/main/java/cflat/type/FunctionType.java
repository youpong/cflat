package cflat.type;

public class FunctionType extends Type {
    protected Type returnType;
    protected ParamTypes paramTypes;
    
    public FunctionType(Type ret, ParamTypes partypes) {
	returnType = ret;
	paramTypes = partypes;
    }
}
