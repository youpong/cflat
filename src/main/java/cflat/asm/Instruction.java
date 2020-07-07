package cflat.asm;

import cflat.utils.TextUtils;

/**
 * Instruction
 */
public class Instruction extends Assembly {
    protected String mnemonic;
    protected String suffix;
    protected Operand[] operands;
    protected boolean needRelocation;

    public Instruction(String mnemonic) {
        this(mnemonic, "", new Operand[0], false);
    }

    public Instruction(String mnemonic, String suffix, Operand a1) {
        this(mnemonic, suffix, new Operand[]{a1}, false);
    }

    public Instruction(String mnemonic, String suffix, Operand a1, Operand a2) {
        this(mnemonic, suffix, new Operand[]{a1, a2}, false);
    }

    public Instruction(String mnemonic, String suffix, Operand a1, Operand a2,
            boolean reloc) {
        this(mnemonic, suffix, new Operand[]{a1, a2}, reloc);
    }

    public Instruction(String mnemonic, String suffix, Operand[] operands,
            boolean reloc) {
        this.mnemonic = mnemonic;
        this.suffix = suffix;
        this.operands = operands;
        this.needRelocation = reloc;
    }

    //    public Instruction build(String mnemonic, Operand o1)
    //    public Instruction build(String mnemonic, Operand o1, Operand o2)

    public boolean isInstruction() {
        return true;
    }

    // ...

    public String toSource(SymbolTable table) {
        StringBuffer buf = new StringBuffer();
        buf.append("\t");
        buf.append(mnemonic + suffix);
        String sep = "\t";
        for (int i = 0; i < operands.length; i++) {
            buf.append(sep);
            sep = ", ";
            buf.append(operands[i].toSource(table));
        }
        return buf.toString();
    }

    /*
     * public String toString() { return "#<Insn " + mnemonic + ">"; }
     */

    public String dump() {
        StringBuffer buf = new StringBuffer();
        buf.append("(Instruction ");
        buf.append(TextUtils.dumpString(mnemonic));
        buf.append(" ");
        buf.append(TextUtils.dumpString(suffix));
        for (Operand oper : operands) {
            buf.append(" ").append(oper.dump());
        }
        buf.append(")");
        return buf.toString();
    }
}
