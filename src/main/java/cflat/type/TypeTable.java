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
    
    // TODO: implement    
    public void semanticCheck(ErrorHandler errorHandler) {
    }
}
