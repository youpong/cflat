package cflat.type;

import cflat.utils.ErrorHandler;
import java.util.*;

/**
 * 型定義のチェック
 * Type/TypeRef の対応を保持
 */
public class TypeTable {
    static public TypeTable ilp32() { return newTable(1, 2, 4, 4, 4); }

    // TODO: implement
    static private TypeTable newTable(int charsize, int shortsize,
				      int intsize, int longsize, int ptrsize) {
	TypeTable table = new TypeTable(intsize, longsize, ptrsize);
	// table.put(new VoidTypeRef(), new VoidType());
	return table;
    }
    
    private int intSize;
    private int longSize;
    private int pointerSize;
    private Map<TypeRef, Type> table;

    public TypeTable(int intSize, int longSize, int pointerSize) {
	this.intSize = intSize;
	this.longSize = longSize;
	this.pointerSize = pointerSize;
	this.table = new HashMap<TypeRef, Type>();
    }

    public boolean isDefined(TypeRef ref) {
	return table.containsKey(ref);
    }

    public void put(TypeRef ref, Type t) {
	if (table.containsKey(ref)) {
	    throw new Error("duplicated type definition: " + ref);
	}
	table.put(ref, t);
    }

    // TODO: test20
    public Type get(TypeRef ref) {
	Type type = table.get(ref);
	if (type == null) {
	    if (ref instanceof UserTypeRef) {
		UserTypeRef uref = (UserTypeRef)ref;
		throw new Error("undefined type: " + uref.name());
	    }
	    else if(ref instanceof PointerTypeRef) {
		PointerTypeRef pref = (PointerTypeRef)ref;
		Type t = new PointerType(pointerSize, get(pref.baseType()));
		table.put(pref, t);
		return t;
	    }
	    else if(ref instanceof ArrayTypeRef) {
		ArrayTypeRef aref = (ArrayTypeRef)ref;
		Type t = new ArrayType(get(aref.baseType()),
				       aref.length(), pointerSize);
		table.put(aref, t);
		return t;
	    }
	    else if(ref instanceof FunctionTypeRef) {
		FunctionTypeRef fref = (FunctionTypeRef)ref;
		Type t = new FunctionType(get(fref.returnType()),
					  fref.params().internTypes(this));
		table.put(fref, t);
		return t;
	    }
	    throw new Error("unregistered type: " + ref.toString());
	}
	return type;
    }

    // TODO: test20
    public Type getParamType(TypeRef ref) {
	Type t = get(ref);
	return t.isArray() ? pointerTo(t.baseType()) : t;
    }

    // TODO: test20
    public PointerType pointerTo(Type baseType) {
	return new PointerType(pointerSize, baseType);
    }
    // TODO: implement    
    public void semanticCheck(ErrorHandler errorHandler) {
    }
}
