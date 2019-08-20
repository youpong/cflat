package cflat.entity;

import cflat.ast.Dumper;
import cflat.ast.TypeNode;

/**
 * 変数宣言
 */
public class UndefinedVariable extends Variable {
    public UndefinedVariable(TypeNode t, String name) {
	super(false, t, name);
    }

    public boolean isPrivate() { return false; }
    
    protected void _dump(Dumper d) {
	d.printMember("name", name);
	d.printMember("isPrivate", isPrivate());
	d.printMember("typeNode", typeNode);
    }

    public <T> T accept(EntityVisitor<T> visitor) {
	return visitor.visit(this);
    }
}
