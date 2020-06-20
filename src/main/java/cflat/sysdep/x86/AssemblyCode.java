package cflat.sysdep.x86;

import cflat.asm.Assembly;
import cflat.asm.Comment;
import cflat.asm.Instruction;
import cflat.asm.Operand;
import cflat.asm.SymbolTable;
import cflat.asm.Type;
import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;

public class AssemblyCode implements cflat.sysdep.AssemblyCode {
    final Type naturalType;
    final long stackWordSize;
    final SymbolTable labelSymbols;
    final boolean verbose;
    //    final VirtualStack virtualStack = new VirtualStack();
    private List<Assembly> assemblies = new ArrayList<Assembly>();
    private int commentIndentLevel = 0;
    //    private Statistics statistics;

    AssemblyCode(Type naturalType, long stackWordSize,
		 SymbolTable labelSymbols, boolean verbose) {
	this.naturalType = naturalType;
	this.stackWordSize = stackWordSize;
	this.labelSymbols = labelSymbols;
	this.verbose = verbose;
    }

    //...

    // 34
    public String toSource() {
	StringBuffer buf = new StringBuffer();
	/* TODO
	for (Assembly asm : assemblies) {
	    buf.append(asm.toSource(labelSymbols));
	    buf.append("\n");
	}
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
	switch(t) {
	case INT8: return "b";
	case INT16: return "w";
	case INT32: return "l";
	case INT64: return "q";
	default:
	    throw new Error("unknown register type: " + t.size());
	}
    }

    // ...

    //
    // instructions
    //

    // ...
    
    // 343
    void sete(Register reg) {
	insn("sete", reg);
    }
    
    // ...

    // 383
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
    
    // ...

    // 472
    void not(Register reg) {
	insn(reg.type, "not", reg);
    }

    // ...
}
