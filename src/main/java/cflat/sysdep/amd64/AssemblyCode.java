package cflat.sysdep.amd64;

import java.io.PrintStream;
import java.util.List;

import cflat.asm.Assembly;
import cflat.asm.Instruction;
import cflat.asm.Label;
import cflat.asm.Operand;
import cflat.asm.Symbol;
import cflat.asm.Type;

public class AssemblyCode implements cflat.sysdep.AssemblyCode {
	// same
	private Type naturalType;
	// same
	private List<Assembly> assemblies;

	// same
	private void insn(String op, String typeSuffix, Operand a) {
		assemblies.add(new Instruction(op, typeSuffix, a));
	}
	
	// same
	protected void insn(Type t, String op, Operand a, Operand b) {
		assemblies.add(new Instruction(op, typeSuffix(t), a, b));
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

	// TODO: implement
	void add(Operand val, Register reg) {
	}

	// TODO: implement
	void sub(Operand val, Register reg) {
	}

	// TODO: implemnt
	void imul(Operand val, Register reg) {
	}

	// TODO: implemnt
	void call(Symbol sym) {
	}

	// TODO: implement
	void ret() {
	}

	// TODO: implement
	void jmp(Label lab) {
	}

	@Override
	public String toSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dump() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dump(PrintStream s) {
		// TODO Auto-generated method stub

	}
}
