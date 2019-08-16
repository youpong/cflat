package cflat.compiler;

import cflat.ast.AST;
import cflat.type.TypeTable;
import cflat.utils.ErrorHandler;

/**
 * 式の妥当性のチェック
 */
class DereferenceChecker
//    extends Visitor
{
    private TypeTable types;
    private ErrorHandler errorHandler;
    public DereferenceChecker(TypeTable types, ErrorHandler errorHandler) {
	this.types = types;
	this.errorHandler = errorHandler;
    }
    public void check(AST ast) {
    }
}
