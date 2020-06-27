package cflat.entity;

import cflat.ast.Dumper;
import cflat.ast.TypeNode;

public class Parameter extends DefinedVariable {
    public Parameter(TypeNode type, String name) {
        super(false, type, name, null);
    }

    // public boolean isParameter()

    protected void _dump(Dumper d) {
        d.printMember("name", name);
        d.printMember("typeNode", typeNode);
    }
}
