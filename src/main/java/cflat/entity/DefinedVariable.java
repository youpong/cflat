package cflat.entity;

import cflat.ast.Dumper;
import cflat.ast.ExprNode;
import cflat.ast.TypeNode;
import cflat.ir.Expr;

/**
 * 変数定義
 */
public class DefinedVariable extends Variable {
    protected ExprNode initializer;
    protected Expr ir;
    protected long sequence;
    
    public DefinedVariable(boolean priv, TypeNode type,
			   String name, ExprNode init) {
	super(priv, type, name);
	this.initializer = init;
	this.sequence = -1;
    }
    //    public boolean isDefined() 
    public boolean hasInitializer() {
	return (initializer != null);
    }
    public ExprNode initializer() {
	return initializer;
    }
    public void setInitializer(ExprNode expr) {
	this.initializer = expr;
    }
    //    setIR
    public Expr ir() { return ir; }
    
    protected void _dump(Dumper d) {
	d.printMember("name", name);
	d.printMember("isPrivate", isPrivate);
	d.printMember("typeNode", typeNode);
	d.printMember("initializer", initializer);
    }
    public <T> T accept(EntityVisitor<T> visitor) {
	return visitor.visit(this);
    }
}
