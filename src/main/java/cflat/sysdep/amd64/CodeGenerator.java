package cflat.sysdep.amd64;

import cflat.asm.DirectMemoryReference;
import cflat.asm.ImmediateValue;
import cflat.asm.IndirectMemoryReference;
import cflat.asm.Label;
import cflat.asm.Symbol;
import cflat.asm.SymbolTable;
import cflat.asm.Type;
import cflat.entity.DefinedFunction;
import cflat.ir.Addr;
import cflat.ir.Assign;
import cflat.ir.Bin;
import cflat.ir.CJump;
import cflat.ir.Call;
import cflat.ir.Expr;
import cflat.ir.ExprStmt;
import cflat.ir.IR;
import cflat.ir.IRVisitor;
import cflat.ir.Int;
import cflat.ir.Jump;
import cflat.ir.LabelStmt;
import cflat.ir.Mem;
import cflat.ir.Return;
import cflat.ir.Stmt;
import cflat.ir.Str;
import cflat.ir.Switch;
import cflat.ir.Uni;
import cflat.ir.Var;
import cflat.sysdep.amd64.AssemblyCode;
import cflat.sysdep.CodeGeneratorOptions;
import cflat.sysdep.amd64.ELFConstants;
import cflat.sysdep.amd64.Register;
import cflat.utils.ErrorHandler;

public class CodeGenerator implements cflat.sysdep.CodeGenerator, IRVisitor<Void, Void>, ELFConstants {
	final CodeGeneratorOptions options;
	final Type naturalType;
	final ErrorHandler errorHandler;
	private AssemblyCode as;
	private Label epilogue;

	public CodeGenerator(CodeGeneratorOptions opts, Type naturalType, ErrorHandler h) {
		this.options = opts;
		this.naturalType = naturalType;
		this.errorHandler = h;
	}

	private ImmediateValue imm(long num) {
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
		locateSymbols(ir);
		return generateAssemblyCode(ir);
	}

	static final String LABEL_SYMBOL_BASE = ".L";

	private void locateSymbols(IR ir) {
		// TODO Auto-generated method stub
	}

	private AssemblyCode generateAssemblyCode(IR ir) {
		// TODO Auto-generated method stub
		return null;
	}

	private AssemblyCode compileStmts(DefinedFunction func) {
		as = newAssemblyCode();
		epilogue = new Label();
		for (Stmt s : func.ir()) {
			compileStmt(s);
		}
		as.label(epilogue);
		return as;
	}

	// same
	private void compileStmt(Stmt stmt) {
		if (options.isVerboseAsm()) {
			if (stmt.location() != null) {
				as.comment(stmt.location().numberedLine());
			}
		}
		stmt.accept(this);
	}

	// same
	private AssemblyCode newAssemblyCode() {
		return new AssemblyCode(naturalType, STACK_WORD_SIZE, new SymbolTable(LABEL_SYMBOL_BASE),
				options.isVerboseAsm());
	}

	//
	// Compile Function
	//

	// 64 bit
	private static final long STACK_WORD_SIZE = 8;

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
	public Void visit(Uni node) {
		// check difference
		Type src = node.expr().type();
		Type dest = node.type();

		compile(node.expr());
		switch (node.op()) {
		case UMINUS:
			as.neg(ax(src));
			break;
		case BIT_NOT:
			as.not(ax(src));
			break;
		case NOT:
			as.test(ax(src), ax(src));
			as.sete(al());
			as.movzx(al(), ax(dest));
			break;
		case S_CAST:
			as.movsx(ax(src), ax(dest));
			break;
		case U_CAST:
			as.movzx(ax(src), ax(dest));
			break;
		default:
			throw new Error("unknown unary operator: " + node.op());
		}
		return null;
	}



	private void compile(Expr expr) {
		// TODO Auto-generated method stub

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
	public Void visit(Int node) {
		as.mov(imm(node.value()), ax());
		return null;
	}

	@Override
	public Void visit(Str node) {
		// TODO 64bit test
		loadConstant(node, ax());
		return null;
	}

	private void loadConstant(Expr node, Register reg) {
		// TODO 64 bit test
		if (node.asmValue() != null) {
			as.mov(node.asmValue(), reg);
		} else if (node.memref() != null) {
			as.lea(node.memref(), reg);
		} else {
			throw new Error("must not happen: constant has no asm value");
		}
	}

	private Register ax() {
		return ax(naturalType);
	}
	
	private Register al() {
		return ax(Type.INT8);
	}

	private Register ax(Type t) {
		return new Register(RegisterClass.AX, t);
	}

}
