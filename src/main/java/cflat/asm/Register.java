package cflat.asm;

/**
 * operand of register like %eax
 */
abstract public class Register extends Operand {

    public boolean isRegister() {
        return true;
    }

    public void collectStatistics(Statistics stats) {
        stats.registerUsed(this);
    }

    abstract public String toSource(SymbolTable table);
    abstract public String dump();
}
