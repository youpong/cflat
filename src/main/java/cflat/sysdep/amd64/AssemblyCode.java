package cflat.sysdep.amd64;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import cflat.asm.Assembly;
import cflat.asm.Comment;
import cflat.asm.DirectMemoryReference;
import cflat.asm.Directive;
import cflat.asm.ImmediateValue;
import cflat.asm.Instruction;
import cflat.asm.Label;
import cflat.asm.MemoryReference;
import cflat.asm.Operand;
import cflat.asm.Symbol;
import cflat.asm.SymbolTable;
import cflat.asm.Type;
import cflat.utils.TextUtils;

public class AssemblyCode implements cflat.sysdep.AssemblyCode {
	final Type naturalType;
	final long stackWordSize;
	final SymbolTable labelSymbols;
	final boolean verbose;
	private List<Assembly> assemblies = new ArrayList<Assembly>();
	private int commentIndentLevel = 0;
	
	AssemblyCode(Type naturalType, long stackWordSize, SymbolTable labelSymbols, boolean verbose){
		this.naturalType = naturalType;
		this.stackWordSize = stackWordSize;
		this.labelSymbols = labelSymbols;
		this.verbose = verbose;
	}
	
	@Override
	public String toSource() {
		StringBuffer buf = new StringBuffer();
		for (Assembly asm : assemblies) {
			buf.append(asm.toSource(labelSymbols));
			buf.append("\n");
		}
		return buf.toString();
	}

	@Override
	public void dump() {
		dump(System.out);
	}

	@Override
	public void dump(PrintStream s) {
		// TODO Auto-generated method stub
		for (Assembly asm : assemblies) {
			s.println(asm.dump());
		}
	}
	// same
	void comment(String str) {
		assemblies.add(new Comment(str, commentIndentLevel));
	}
	// same
	void indentComment() {
		commentIndentLevel++;
	}
	// same
	void unindentComment() {
		commentIndentLevel--;
	}
	// same
	void label(Symbol sym) {
		assemblies.add(new Label(sym));
	}
	// same
	void label(Label label) {
		assemblies.add(label);
	}
	
	// same
	private void directive(String direc) {
		assemblies.add(new Directive(direc));
	}
	
	// same
	private void insn(String op, String typeSuffix, Operand a) {
		assemblies.add(new Instruction(op, typeSuffix, a));
	}
	
	// same
	protected void insn(Type t, String op, Operand a, Operand b) {
		assemblies.add(new Instruction(op, typeSuffix(t), a, b));
	}
	// same
	private void insn(Type t, String op, Operand a) {
		assemblies.add(new Instruction(op, typeSuffix(t), a));
	}
	
	// same
	protected void insn(String op, Operand a) {
		assemblies.add(new Instruction(op, "", a));
	}
	// same
	protected void insn(String op) {
		assemblies.add(new Instruction(op));
	}
	
	// same x86
	private String typeSuffix(Type t) {
		switch (t) {
		case INT8:
			return "b";
		case INT16:
			return "w";
		case INT32:
			return "l";
		case INT64:
			return "q";
		default:
			throw new Error("unknown register type: " + t.size());
		}
	}
	
	//
	// directives
	//
	
	// same
	void _file(String name) {
		directive(".file\t" + TextUtils.dumpString(name));
	}
	// same
	void _globl(Symbol sym) {
		directive(".globl " + sym.name());
	}
	// same
	void _section(String name) {
		directive("\t.section\t" + name);
	}	

	// TODO: same
	void mov(Register src, Register dest) {
		insn(naturalType, "mov", src, dest);
	}

	// TODO: same
	void mov(Register src, Operand dest) {
		insn(naturalType, "mov", src, dest);		
	}

	// TODO: same
	void mov(Operand src, Register dest) {
		insn(naturalType, "mov", src, dest);
	}

	// TODO: same
	void push(Register reg) {
		insn("push", typeSuffix(naturalType), reg);
	}

	// TODO: same
	void pop(Register reg) {
		insn("pop", typeSuffix(naturalType), reg);
	}

	// TODO: same
	void add(Operand diff, Register base) {
		insn(base.type, "add", diff, base);
	}

	// TODO: same
	void sub(Operand diff, Register base) {
		insn(base.type, "sub", diff, base);
	}

	// TODO: same
	void imul(Operand m, Register base) {
		insn(base.type, "imul", m, base);
	}

	// TODO: implemnt
	void call(Symbol sym) {
		insn("call", new DirectMemoryReference(sym));
	}

	// TODO: same
	void ret() {
		insn("ret");
	}

	// TODO: same
	void jmp(Label label) {
		insn("jmp", new DirectMemoryReference(label.symbol()));
	}
	
	// same
	public void lea(Operand src, Register dest) {
		insn(naturalType, "lea", src, dest);
	}

	public void neg(Register ax) {
		// TODO Auto-generated method stub
		
	}

	public void not(Register ax) {
		// TODO Auto-generated method stub
		
	}

	public void test(Register ax, Register ax2) {
		// TODO Auto-generated method stub
		
	}

	public void sete(Object al) {
		// TODO Auto-generated method stub
		
	}

	public void movzx(Object al, Register ax) {
		// TODO Auto-generated method stub
		
	}

	public void movsx(Register ax, Register ax2) {
		// TODO Auto-generated method stub
		
	}

	public void virtualPush(Register ax) {
		// TODO Auto-generated method stub
		
	}

	public void virtualPop(Register cx) {
		// TODO Auto-generated method stub
		
	}

	public void sal(Register bits, Register base) {
		insn(base.type, "sal", bits, base);
	}

	public void shr(Object cl, Register left) {
		// TODO Auto-generated method stub
		
	}

	public void sar(Object cl, Register left) {
		// TODO Auto-generated method stub
		
	}

	public void and(Operand right, Register left) {
		// TODO Auto-generated method stub
		
	}

	public void or(Operand right, Register left) {
		// TODO Auto-generated method stub
		
	}

	public void xor(Operand right, Register left) {
		// TODO Auto-generated method stub
		
	}

	public void cltd() {
		// TODO Auto-generated method stub
		
	}

	public void idiv(Register base) {
		// TODO Auto-generated method stub
		insn(base.type, "idiv", base);
	}



}
