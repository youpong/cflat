package cflat.asm;

abstract public class Operand implements OperandPattern {
    abstract public String toSource(SymbolTable table);

    abstract public String dump();

    /*
     * public boolean isRegister() { return false; }
     */

    // ...

    abstract public void collectStatistics(Statistics stats);

    public boolean match(Operand operand) {
        return equals(operand);
    }
}
