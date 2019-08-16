package cflat.compiler;

import cflat.ast.AST;
import cflat.type.TypeTable;
import cflat.utils.ErrorHandler;

/**
 * 型名の解決
 */
public class TypeResolver
//    extends Visitor
{
    private TypeTable types;
    private ErrorHandler errorHandler;
    
    // TODO: implement
    public TypeResolver(TypeTable types, ErrorHandler errorHandler) {
	this.types = types;
	this.errorHandler = errorHandler;
    }

    // TODO: implement
    public void resolve(AST ast) {
    }
}
