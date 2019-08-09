package cflat.ast;

public interface ASTVisitor<S,E> {
    // Statements
    // Expressions
    public E visit(BinaryOpNode node);
}

