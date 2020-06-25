package cflat.entity;

import cflat.asm.Symbol;
import cflat.ast.TypeNode;
import cflat.type.Type;
import java.util.List;

abstract public class Function extends Entity {
    protected Symbol callingSymbol;
    // protected Label label;

    public Function(boolean priv, TypeNode t, String name) {
        super(priv, t, name);
    }

    // public boolean isInitialized() { return true; }

    abstract public boolean isDefined();

    abstract public List<Parameter> parameters();

    public Type returnType() {
        return type().getFunctionType().returnType();
    }

    // public boolean isVoid()
    // public void setCallingSymbol(Symbol sym)

    public Symbol callingSymbol() {
        if (this.callingSymbol == null) {
            throw new Error(
                    "must not happen: Function#callingSymbol called but null");
        }
        return this.callingSymbol;
    }

    // public Label label()
}
