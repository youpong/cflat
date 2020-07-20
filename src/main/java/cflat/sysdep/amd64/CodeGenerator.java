package cflat.sysdep.amd64;

import cflat.asm.DirectMemoryReference;
import cflat.asm.ImmediateValue;
import cflat.asm.IndirectMemoryReference;
import cflat.asm.Symbol;
import cflat.asm.Type;
import cflat.ir.Addr;
import cflat.ir.Assign;
import cflat.ir.Bin;
import cflat.ir.CJump;
import cflat.ir.Call;
import cflat.ir.ExprStmt;
import cflat.ir.IR;
import cflat.ir.IRVisitor;
import cflat.ir.Int;
import cflat.ir.Jump;
import cflat.ir.LabelStmt;
import cflat.ir.Mem;
import cflat.ir.Return;
import cflat.ir.Str;
import cflat.ir.Switch;
import cflat.ir.Uni;
import cflat.ir.Var;
import cflat.sysdep.AssemblyCode;
import cflat.sysdep.CodeGeneratorOptions;
import cflat.sysdep.x86.ELFConstants;
import cflat.sysdep.amd64.Register;
import cflat.utils.ErrorHandler;

public class CodeGenerator implements cflat.sysdep.CodeGenerator, IRVisitor<Void, Void>,ELFConstants
{
	private CodeGeneratorOptions options;
	private Type naturalType;
	private ErrorHandler errorHandler;

	public CodeGenerator(CodeGeneratorOptions opts, Type naturalType, ErrorHandler h) {
		this.options = opts;
		this.naturalType = naturalType;
		this.errorHandler = h;
	}

	private ImmediateValue imm(long num){
		return new ImmediateValue(num);
	}
	
	private ImmediateValue imm(Symbol sym) {
		return new ImmediateValue(sym);
	}
	
	// TODO: Direct or Indirect?
	private DirectMemoryReference mem(Symbol sym) {
		return new DirectMemoryReference(sym);
	}
	
	private IndirectMemoryReference mem(Register reg) {
		return new IndirectMemoryReference(0, reg);
	}
	
	private IndirectMemoryReference mem(long offset, Register reg) {
		return new IndirectMemoryReference(offset, reg);
	}
	
	private IndirectMemoryReference mem(Symbol offset, Register reg) {
		return new IndirectMemoryReference(offset, reg);
	}
	
	@Override
	public AssemblyCode generate(IR ir) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(ExprStmt s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(Assign s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(CJump s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(Jump s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(Switch s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(LabelStmt s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(Return s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(Uni s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(Bin s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(Call s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(Addr s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(Mem s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(Var s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(Int s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(Str s) {
		// TODO Auto-generated method stub
		return null;
	}

}
