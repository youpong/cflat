package cflat.type;

public class FunctionType extends Type {
    protected Type returnType;
    protected ParamTypes paramTypes;

    public FunctionType(Type ret, ParamTypes partypes) {
        returnType = ret;
        paramTypes = partypes;
    }

    public boolean isFunction() {
        return true;
    }

    public boolean isCallable() {
        return true;
    }

    public boolean isSameType(Type other) {
        if (!other.isFunction())
            return false;
        FunctionType t = other.getFunctionType();
        return t.returnType.isSameType(returnType)
                && t.paramTypes.isSameType(paramTypes);
    }

    public boolean isCompatible(Type target) {
        if (!target.isFunction())
            return false;
        FunctionType t = target.getFunctionType();
        return t.returnType.isCompatible(returnType)
                && t.paramTypes.isSameType(paramTypes);
    }

    public boolean isCastableTo(Type target) {
        return target.isFunction();
    }

    //
    public Type returnType() {
        return returnType;
    }

    public long size() {
        throw new Error("FunctionType#size called");
    }

    public String toString() {
        String sep = "";
        StringBuffer buf = new StringBuffer();
        buf.append(returnType.toString());
        buf.append("(");
        for (Type t : paramTypes.types()) {
            buf.append(sep);
            buf.append(t.toString());
            sep = ", ";
        }
        buf.append(")");
        return buf.toString();
    }
}
