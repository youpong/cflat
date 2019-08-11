package cflat.ast;

public interface ASTVisitor<S,E> {
    // Statements

    // Expressions
    public E visit(AddressNode node);
    public E visit(ArefNode node);
    public E visit(AssignNode node);            
    public E visit(BinaryOpNode node);
    public E visit(CastNode node);
    public E visit(CondExprNode node);
    public E visit(DereferenceNode node);
    public E visit(FuncallNode node);    
    public E visit(IntegerLiteralNode node);
    public E visit(LogicalAndNode node);
    public E visit(LogicalOrNode node);    
    public E visit(MemberNode node);
    public E visit(OpAssignNode node);            
    public E visit(PrefixOpNode node);
    public E visit(PtrMemberNode node);    
    public E visit(SizeofTypeNode node);
    public E visit(SizeofExprNode node);        
    public E visit(StringLiteralNode node);
    public E visit(UnaryArithmeticOpNode node);        
    public E visit(UnaryOpNode node);
    public E visit(VariableNode node);    
}
