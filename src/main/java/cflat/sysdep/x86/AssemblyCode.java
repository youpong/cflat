package cflat.sysdep.x86;

import cflat.asm.Assembly;
import cflat.asm.Comment;
import cflat.asm.IndirectMemoryReference;
import cflat.asm.Instruction;
import cflat.asm.Label;
import cflat.asm.Operand;
import cflat.asm.Symbol;
import cflat.asm.SymbolTable;
import cflat.asm.Type;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class AssemblyCode implements cflat.sysdep.AssemblyCode {
    final Type naturalType;
    final long stackWordSize;
    final SymbolTable labelSymbols;
    final boolean verbose;
    final VirtualStack virtualStack = new VirtualStack();
    private List<Assembly> assemblies = new ArrayList<Assembly>();
    private int commentIndentLevel = 0;
    // private Statistics statistics;

    AssemblyCode(Type naturalType, long stackWordSize, SymbolTable labelSymbols,
            boolean verbose) {
        this.naturalType = naturalType;
        this.stackWordSize = stackWordSize;
        this.labelSymbols = labelSymbols;
        this.verbose = verbose;
    }

    // ...

    // 34
    public String toSource() {
        StringBuffer buf = new StringBuffer();
        /*
         * TODO for (Assembly asm : assemblies) {
         * buf.append(asm.toSource(labelSymbols)); buf.append("\n"); }
         */
        return buf.toString();
    }

    public void dump() {
        dump(System.out);
    }

    public void dump(PrintStream s) {
        for (Assembly asm : assemblies) {
            s.println(asm.dump());
        }
    }

    // ...

    // 68
    void comment(String str) {
        assemblies.add(new Comment(str, commentIndentLevel));
    }

    void indentComment() {
        commentIndentLevel++;
    }

    void unindentComment() {
        commentIndentLevel--;
    }

    void label(Symbol sym) {
        assemblies.add(new Label(sym));
    }

    void label(Label label) {
        assemblies.add(label);
    }

    // ...

    // 106
    protected void insn(String op) {
        assemblies.add(new Instruction(op));
    }

    protected void insn(String op, Operand a) {
        assemblies.add(new Instruction(op, "", a));
    }

    protected void insn(String op, String suffix, Operand a) {
        assemblies.add(new Instruction(op, suffix, a));
    }

    protected void insn(Type t, String op, Operand a) {
        assemblies.add(new Instruction(op, typeSuffix(t), a));
    }

    protected void insn(String op, String suffix, Operand a, Operand b) {
        assemblies.add(new Instruction(op, suffix, a, b));
    }

    protected void insn(Type t, String op, Operand a, Operand b) {
        assemblies.add(new Instruction(op, typeSuffix(t), a, b));
    }

    protected String typeSuffix(Type t1, Type t2) {
        return typeSuffix(t1) + typeSuffix(t2);
    }

    protected String typeSuffix(Type t) {
        switch (t) {
        case INT8 :
            return "b";
        case INT16 :
            return "w";
        case INT32 :
            return "l";
        case INT64 :
            return "q";
        default :
            throw new Error("unknown register type: " + t.size());
        }
    }

    //
    // directives
    //

    // ...

    //
    // Virtual Stack
    //
    class VirtualStack {
        private long offset;
        private long max;
        private List<IndirectMemoryReference> memrefs = new ArrayList<IndirectMemoryReference>();

        VirtualStack() {
            reset();
        }

        void reset() {
            offset = 0;
            max = 0;
            memrefs.clear();
        }

        // long maxSize()
        void extend(long len) {
            offset += len;
            max = Math.max(offset, max);
        }

        void rewind(long len) {
            offset -= len;
        }

        IndirectMemoryReference top() {
            IndirectMemoryReference mem = relocatableMem(-offset, bp());
            memrefs.add(mem);
            return mem;
        }

        private IndirectMemoryReference relocatableMem(long offset, Register base) {
            return IndirectMemoryReference.relocatable(offset, base);
        }

        private Register bp() {
            return new Register(RegisterClass.BP, naturalType);
        }
        // void fixOffset(long diff)
    }

    // ...

    // 304
    void virtualPush(Register reg) {
        if (verbose) {
            comment("push " + reg.baseName() + " -> " + virtualStack.top());
        }
        virtualStack.extend(stackWordSize);
        mov(reg, virtualStack.top());
    }

    void virtualPop(Register reg) {
        if (verbose) {
            comment("pop " + reg.baseName() + " <- " + virtualStack.top());
        }
        mov(virtualStack.top(), reg);
        virtualStack.rewind(stackWordSize);
    }

    //
    // instructions
    //

    // ...

    // 339
    void cmp(Operand a, Register b) {
        insn(b.type, "cmp", a, b);
    }

    void sete(Register reg) {
        insn("sete", reg);
    }

    void setne(Register reg) {
        insn("setne", reg);
    }

    void seta(Register reg) {
        insn("seta", reg);
    }

    void setae(Register reg) {
        insn("setae", reg);
    }

    void setb(Register reg) {
        insn("setb", reg);
    }

    void setbe(Register reg) {
        insn("setbe", reg);
    }

    void setg(Register reg) {
        insn("setg", reg);
    }

    void setge(Register reg) {
        insn("setge", reg);
    }

    void setl(Register reg) {
        insn("setl", reg);
    }

    void setle(Register reg) {
        insn("setle", reg);
    }

    void test(Register a, Register b) {
        insn(b.type, "test", a, b);
    }

    // ...

    // 409
    void mov(Register src, Register dest) {
        insn(naturalType, "mov", src, dest);
    }

    void mov(Operand src, Register dest) {
        insn(dest.type, "mov", src, dest);
    }

    void mov(Register src, Operand dest) {
        insn(src.type, "mov", src, dest);
    }

    // ...

    // 428
    void movsx(Register src, Register dest) {
        insn("movs", typeSuffix(src.type, dest.type), src, dest);
    }

    void movzx(Register src, Register dest) {
        insn("movz", typeSuffix(src.type, dest.type), src, dest);
    }

    // ...

    // 440
    void lea(Operand src, Register dest) {
        insn(naturalType, "lea", src, dest);
    }

    void neg(Register reg) {
        insn(reg.type, "neg", reg);
    }

    void add(Operand diff, Register base) {
        insn(base.type, "add", diff, base);
    }

    void sub(Operand diff, Register base) {
        insn(base.type, "sub", diff, base);
    }

    void imul(Operand m, Register base) {
        insn(base.type, "imul", m, base);
    }

    void cltd() {
        insn("cltd");
    }

    void div(Register base) {
        insn(base.type, "div", base);
    }

    void idiv(Register base) {
        insn(base.type, "idiv", base);
    }

    void not(Register reg) {
        insn(reg.type, "not", reg);
    }

    void and(Operand bits, Register base) {
        insn(base.type, "and", bits, base);
    }

    void or(Operand bits, Register base) {
        insn(base.type, "or", bits, base);
    }

    void xor(Operand bits, Register base) {
        insn(base.type, "xor", bits, base);
    }

    void sar(Register bits, Register base) {
        insn(base.type, "sar", bits, base);
    }

    void sal(Register bits, Register base) {
        insn(base.type, "sal", bits, base);
    }

    void shr(Register bits, Register base) {
        insn(base.type, "shr", bits, base);
    }
}
