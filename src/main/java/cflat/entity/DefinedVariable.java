package cflat.entity;

import cflat.ast.Dumper;
import cflat.ast.ExprNode;
import cflat.ast.TypeNode;

public class DefinedVariable extends Variable {
    protected ExprNode initializer;
    protected long sequence;
    
    public DefinedVariable(boolean priv, TypeNode type,
			   String name, ExprNode init) {
	super(priv, type, name);
	this.initializer = init;
	this.sequence = -1;
    }
    public boolean hasInitializer() {
	return (initializer != null);
    }
    public ExprNode initializer() {
	return initializer;
    }
    protected void _dump(Dumper d) {
	d.printMember("name", name);
	d.printMember("isPrivate", isPrivate);
	d.printMember("typeNode", typeNode);
	d.printMember("initializer", initializer);
    }
}
