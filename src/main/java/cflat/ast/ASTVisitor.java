package cflat.ast;

public interface ASTVisitor<S,E> {
    // Statements

    // Expressions
    public E visit(BinaryOpNode node);
    public E visit(CastNode node);
    public E visit(VariableNode node);    
    public E visit(IntegerLiteralNode node);
    public E visit(StringLiteralNode node);    
}

