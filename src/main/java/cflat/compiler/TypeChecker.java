package cflat.compiler;

import cflat.ast.AST;
import cflat.type.TypeTable;
import cflat.utils.ErrorHandler;

/**
 * 静的型チェック
 */
public class TypeChecker
//    extends Visitor
{
    private TypeTable types;
    private ErrorHandler errorHandler;

    // TODO: implement
    public TypeChecker(TypeTable types, ErrorHandler errorHandler) {
	this.types = types;
	this.errorHandler = errorHandler;
    }

    // TODO: implement    
    public void check(AST ast) {
    }
}
