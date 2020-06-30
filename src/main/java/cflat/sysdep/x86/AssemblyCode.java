package cflat.sysdep.x86;

import cflat.asm.AbsoluteAddress;
import cflat.asm.Assembly;
import cflat.asm.Comment;
import cflat.asm.Directive;
import cflat.asm.DirectMemoryReference;
import cflat.asm.IndirectMemoryReference;
import cflat.asm.Instruction;
import cflat.asm.IntegerLiteral;
import cflat.asm.Label;
import cflat.asm.Literal;
import cflat.asm.Operand;
import cflat.asm.Statistics;
import cflat.asm.Symbol;
import cflat.asm.SymbolTable;
import cflat.asm.Type;
import cflat.utils.TextUtils;
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
    private Statistics statistics;

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
        for (Assembly asm : assemblies) {
            buf.append(asm.toSource(labelSymbols));
            buf.append("\n");
        }
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

    // 57
    private Statistics statistics() {
        if (statistics == null) {
            statistics = Statistics.collect(assemblies);
        }
        return statistics;
    }

    boolean doesUses(Register reg) {
        return statistics().doesRegisterUsed(reg);
    }

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

    // 102
    protected void directive(String direc) {
        assemblies.add(new Directive(direc));
    }

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

    void _file(String name) {
        directive(".file\t" + TextUtils.dumpString(name));
    }

    void _text() {
        directive("\t.text");
    }

    void _data() {
        directive("\t.data");
    }

    void _section(String name) {
        directive("\t.section\t" + name);
    }

    void _section(String name, String flags, String type, String group,
            String linkage) {
        directive("\t.section\t" + name + "," + flags + "," + type + "," + group + ","
                + linkage);
    }

    void _globl(Symbol sym) {
        directive(".globl " + sym.name());
    }

    void _local(Symbol sym) {
        directive(".local " + sym.name());
    }

    void _hidden(Symbol sym) {
        directive("\t.hidden\t" + sym.name());
    }

    void _comm(Symbol sym, long size, long alignment) {
        directive("\t.comm\t" + sym.name() + "," + size + "," + alignment);
    }

    void _align(long n) {
        directive("\t.align\t" + n);
    }

    void _type(Symbol sym, String type) {
        directive("\t.type\t" + sym.name() + "," + type);
    }

    void _size(Symbol sym, long size) {
        _size(sym, new Long(size).toString());
    }

    void _size(Symbol sym, String size) {
        directive("\t.size\t" + sym.name() + "," + size);
    }

    void _byte(long val) {
        directive(".byte\t" + new IntegerLiteral((byte) val).toSource());
    }

    void _value(long val) {
        directive(".value\t" + new IntegerLiteral((short) val).toSource());
    }

    void _long(long val) {
        directive(".long\t" + new IntegerLiteral((int) val).toSource());
    }

    void _quad(long val) {
        directive(".quad\t" + new IntegerLiteral(val).toSource());
    }
    /*
    void _byte(Literal val) {
        directive(".byte\t" + val.toSource());
    }
    */
    /*
    void _value(Literal val) {
        directive(".value\t" + val.toSource());
    }
    */
    void _long(Literal val) {
        directive(".long\t" + val.toSource());
    }

    void _quad(Literal val) {
        directive(".quad\t" + val.toSource());
    }

    void _string(String str) {
        directive("\t.string\t" + TextUtils.dumpString(str));
    }

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

        long maxSize() {
            return max;
        }

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

        void fixOffset(long diff) {
            for (IndirectMemoryReference mem : memrefs) {
                mem.fixOffset(diff);
            }
        }
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

    void jmp(Label label) {
        insn("jmp", new DirectMemoryReference(label.symbol()));
    }

    void jnz(Label label) {
        insn("jnz", new DirectMemoryReference(label.symbol()));
    }

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

    void push(Register reg) {
        insn("push", typeSuffix(naturalType), reg);
    }

    // void pop(Register reg)

    /** call function by relative address */
    void call(Symbol sym) {
        insn("call", new DirectMemoryReference(sym));
    }

    /** call function by absolute address */
    void callAbsolute(Register reg) {
        insn("call", new AbsoluteAddress(reg));
    }

    void ret() {
        insn("ret");
    }

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
