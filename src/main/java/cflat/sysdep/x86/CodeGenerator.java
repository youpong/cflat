package cflat.sysdep.x86;

import cflat.asm.BaseSymbol;
import cflat.asm.DirectMemoryReference;
import cflat.asm.ImmediateValue;
import cflat.asm.IndirectMemoryReference;
import cflat.asm.Literal;
import cflat.asm.MemoryReference;
import cflat.asm.Symbol;
import cflat.asm.SymbolTable;
import cflat.asm.Type;
import cflat.entity.ConstantEntry;
import cflat.entity.Entity;
import cflat.entity.Function;
import cflat.entity.Variable;
import cflat.ir.Addr;
import cflat.ir.Assign;
import cflat.ir.Bin;
import cflat.ir.Call;
import cflat.ir.CJump;
import cflat.ir.Expr;
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
import cflat.sysdep.CodeGeneratorOptions;
import cflat.utils.ErrorHandler;

public class CodeGenerator implements cflat.sysdep.CodeGenerator,
				      IRVisitor<Void,Void>, ELFConstants {
    final CodeGeneratorOptions options;
    final Type naturalType;
    final ErrorHandler errorHandler;
    
    public CodeGenerator(CodeGeneratorOptions options,
			 Type naturalType,
			 ErrorHandler errorHandler) {
	this.options = options;
	this.naturalType = naturalType;
	this.errorHandler = errorHandler;
    }
    
    /** Compiles IR and generates assembly code. */
    public AssemblyCode generate(IR ir) {
	locateSymbols(ir);
	return generateAssemblyCode(ir);
    }

    static final String LABEL_SYMBOL_BASE = ".L";    
    static final String CONST_SYMBOL_BASE = ".LC";

    //
    // locateSymbols
    //
    private void locateSymbols(IR ir) {
	SymbolTable constSymbols = new SymbolTable(CONST_SYMBOL_BASE);
	for (ConstantEntry ent : ir.constantTable().entries()) {
	    locateStringLiteral(ent, constSymbols);
	}
	for (Variable var : ir.allGlobalVariables()) {
	    locateGlobalVariable(var);
	}
	for (Function func : ir.allFunctions()) {
	    locateFunction(func);
	}
    }

    private void locateStringLiteral(ConstantEntry ent, SymbolTable syms) {
	// TODO
    }

    private void locateGlobalVariable(Entity ent) {
	// TODO
    }

    private void locateFunction(Function func) {
	// TODO
    }
    
    //...

    //
    // generateAssemblyCode
    //

    private AssemblyCode generateAssemblyCode(IR ir) {
	// TODO
	return null;
    }
    
    // 483
    private AssemblyCode as;
    // private Label epilogue;
    
    public Void visit(Uni node) {
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
    
    public Void visit(Bin node) {
	// TODO
	return null;
    }
    
    /**
     * Implements cdecl function call:
     *    * All arguments are on stack.
     *    * Caller rewinds stack pointer.
     */
    public Void visit(Call node) {
	// TODO
	return null;
    }
    public Void visit(Return node) {
	// TODO
	return null;
    }

    public Void visit(Var node) {
	loadVariable(node, ax());
	return null;
    }
    
    public Void visit(Int node) {
	as.mov(imm(node.value()), ax());
	return null;
    }

    public Void visit(Str node) {
	loadConstant(node, ax());
	return null;
    }

    // Assignable expressions

    public Void visit(Assign node) {
	// TODO:
	return null;
    }
    
    public Void visit(Mem node) {
	compile(node.expr());
	load(mem(ax()), ax(node.type()));
	return null;
    }
    public Void visit(Addr node) {
	loadAddress(node.entity(), ax());
	return null;
    }

    //
    // 679 Statements
    //

    // 694
    public Void visit(ExprStmt stmt) {
	// TODO
	return null;
    }
    
    public Void visit(LabelStmt node) {
	// TODO
	return null;
    }
    
    public Void visit(Jump node) {
	// TODO
	return null;
    }

    public Void visit(CJump node) {
	// TODO
	return null;
    }
    // ...
    public Void visit(Switch node) {
	// TODO
	return null;
    }

    //
    // Expressions
    //
    
    private void compile(Expr n) {
	if (options.isVerboseAsm()) {
	    as.comment(n.getClass().getSimpleName() + " {");
	    as.indentComment();
	}
	n.accept(this);
	if (options.isVerboseAsm()) {
	    as.unindentComment();
	    as.comment("}");
	}
    }
    //...
    
    //
    // 994 Utilities
    //

    /** 
     * Loads constant value.  You must check node by #isConstant
     * before calling this method.
     */
    private void loadConstant(Expr node, Register reg) {
	if (node.asmValue() != null) {
	    as.mov(node.asmValue(), reg);
	} else if (node.memref() != null) {
	    as.lea(node.memref(), reg);
	} else {
	    throw new Error("must not happen: constant has no asm value");
	}
    }

    /** Loads variable content to the register. */
    private void loadVariable(Var var, Register dest) {
	if (var.memref() == null) {
	    Register a = dest.forType(naturalType);
	    as.mov(var.address(), a);
	    load(mem(a), dest.forType(var.type()));
	} else {
	    load(var.memref(), dest.forType(var.type()));
	}
    }
    
    /** Loads the address of the variable to the register. */
    private void loadAddress(Entity var, Register dest) {
	if (var.address() != null) {
	    as.mov(var.address(), dest);
	} else {
	    as.lea(var.memref(), dest);
	}
    }
    
    private Register ax() { return ax(naturalType); }
    private Register al() { return ax(Type.INT8); }

    // ...
    
    // 1051
    private Register ax(Type t) {
	return new Register(RegisterClass.AX, t);
    }
    
    // ...
    
    // 1085
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

    private ImmediateValue imm(long n) {
	return new ImmediateValue(n);
    }
    private ImmediateValue imm(Symbol sym) {
	return new ImmediateValue(sym);
    }
    private ImmediateValue imm(Literal lit) {
	return new ImmediateValue(lit);
    }
    private void load(MemoryReference mem, Register reg) {
	as.mov(mem, reg);
    }
    /*
    private void store(Register reg, MemoryReference mem) {
	as.mov(reg, mem);
    }
    */
}
