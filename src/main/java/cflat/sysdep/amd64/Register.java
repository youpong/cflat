package cflat.sysdep.amd64;

import cflat.asm.SymbolTable;
import cflat.asm.Type;

// TODO: implement
public class Register extends cflat.asm.Register {

    Type type;
    RegisterClass _class;

    public Register(RegisterClass _class, Type type) {
        // TODO Auto-generated constructor stub
        this._class = _class;
        this.type = type;
    }

    @Override
    public String toSource(SymbolTable table) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String dump() {
        // TODO Auto-generated method stub
        return null;
    }

    public Register forType(Type naturalType) {
        // TODO Auto-generated method stub
        return null;
    }

}
