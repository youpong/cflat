package cflat.entity;

import cflat.ast.BlockNode;
import cflat.ast.Dumper;
import cflat.ast.TypeNode;
import java.util.*;

// TODO: implement
/**
 * 関数定義
 */
public class DefinedFunction extends Function {
    protected Params params;
    protected BlockNode body;
    protected LocalScope scope;
    
    public DefinedFunction(boolean priv, TypeNode type, String name,
			   Params params, BlockNode body) {
	super(priv, type, name);
	this.params = params;
	this.body = body;
    }

    public void setScope(LocalScope scope) {
	this.scope = scope;
    }
    public List<Parameter> parameters() {
	return params.parameters();
    }
    public BlockNode body() {
	return body;
    }
    protected void _dump(Dumper d) {
	d.printMember("name", name);
	d.printMember("isPrivate", isPrivate);
	d.printMember("params", params);
	d.printMember("body", body);
    }

    public <T> T accept(EntityVisitor<T> visitor) {
	return visitor.visit(this);
    }
}
