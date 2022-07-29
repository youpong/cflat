package cflat.sysdep.amd64;

import cflat.asm.DirectMemoryReference;
import cflat.asm.ImmediateValue;
import cflat.asm.IndirectMemoryReference;
import cflat.asm.Label;
import cflat.asm.MemoryReference;
import cflat.asm.Operand;
import cflat.asm.Symbol;
import cflat.asm.SymbolTable;
import cflat.asm.Type;
import cflat.entity.DefinedFunction;
import cflat.entity.Entity;
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
import cflat.ir.Op;
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
import cflat.utils.ListUtils;

public class CodeGenerator
        implements
            cflat.sysdep.CodeGenerator,
            IRVisitor<Void, Void>,
            ELFConstants {
    final CodeGeneratorOptions options;
    final Type naturalType;
    final ErrorHandler errorHandler;
    private AssemblyCode as;
    private Label epilogue;

    public CodeGenerator(CodeGeneratorOptions opts, Type naturalType,
            ErrorHandler h) {
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
        return new AssemblyCode(naturalType, STACK_WORD_SIZE,
                new SymbolTable(LABEL_SYMBOL_BASE), options.isVerboseAsm());
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
    public Void visit(Assign node) {
        if (node.lhs().isAddr() && node.lhs().memref() != null) {
            compile(node.rhs());
            store(ax(node.lhs().type()), node.lhs().memref());
        } else if (node.rhs().isConstant()) {
            compile(node.lhs());
            as.mov(ax(), cx());
            loadConstant(node.rhs(), ax());
            store(ax(node.lhs().type()), mem(cx()));
        } else {
            compile(node.rhs());
            as.virtualPush(ax());
            compile(node.lhs());
            as.mov(ax(), cx());
            as.virtualPop(ax());
            store(ax(node.lhs().type()), mem(cx()));
        }
        return null;
    }

    private void store(Register reg, MemoryReference mem) {
        as.mov(reg, mem);
    }

    @Override
    public Void visit(CJump node) {
        compile(node.cond());
        Type t = node.cond().type();
        as.test(ax(t), ax(t));
        as.jnz(node.thenLabel());
        as.jmp(node.elseLabel());
        return null;
    }

    @Override
    public Void visit(Jump node) {
        as.jmp(node.label());
        return null;
    }

    @Override
    public Void visit(Switch s) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visit(LabelStmt node) {
        as.label(node.label());
        return null;
    }

    @Override
    public Void visit(Return node) {
       if (node.expr() != null) {
    		compile(node.expr());
    	}
    	as.jmp(epilogue);
       return null;
    }

    @Override
    public Void visit(Uni node) {
        // check difference
        Type src = node.expr().type();
        Type dest = node.type();

        compile(node.expr());
        switch (node.op()) {
        case UMINUS :
            as.neg(ax(src));
            break;
        case BIT_NOT :
            as.not(ax(src));
            break;
        case NOT :
            as.test(ax(src), ax(src));
            as.sete(al());
            as.movzx(al(), ax(dest));
            break;
        case S_CAST :
            as.movsx(ax(src), ax(dest));
            break;
        case U_CAST :
            as.movzx(ax(src), ax(dest));
            break;
        default :
            throw new Error("unknown unary operator: " + node.op());
        }
        return null;
    }

    private void compile(Expr expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public Void visit(Bin node) {
        // TODO Auto-generated method stub
        Op op = node.op();
        Type t = node.type();

        if (node.right().isConstant() && !doesRequireRegisterOperand(op)) {
            compile(node.left());
            compileBinaryOp(op, ax(t), node.right().asmValue());
        } else if (node.right().isConstant()) {
            compile(node.left());
            loadConstant(node.right(), cx());
            compileBinaryOp(op, ax(t), cx(t));
        } else if (node.right().isVar()) {
            compile(node.left());
            loadVariable((Var) node.right(), cx(t));
            compileBinaryOp(op, ax(t), cx(t));
        } else if (node.right().isAddr()) {
            compile(node.left());
            loadAddress(node.right().getEntityForce(), cx(t));
            compileBinaryOp(op, ax(t), cx(t));
        } else if (node.left().isConstant() || node.left().isVar()
                || node.left().isAddr()) {
            compile(node.right());
            as.mov(ax(), cx());
            compile(node.left());
            compileBinaryOp(op, ax(t), cx(t));
        } else {
            compile(node.right());
            as.virtualPush(ax());
            compile(node.left());
            as.virtualPop(cx());
            compileBinaryOp(op, ax(t), cx(t));
        }
        return null;
    }

    private void loadVariable(Var var, Register dest) {
        // TODO Auto-generated method stub
        if (var.memref() == null) {
            Register a = dest.forType(naturalType);
            as.mov(var.address(), a);
            load(mem(a), dest.forType(var.type()));
        } else {
            load(var.memref(), dest.forType(var.type()));
        }
    }

    private void load(MemoryReference mem, Register reg) {
        as.mov(mem, reg);
    }

    private void loadAddress(Entity var, Register dest) {
        if (var.address() != null) {
            as.mov(var.address(), dest);
        } else {
            as.lea(var.memref(), dest);
        }
    }

    private void compileBinaryOp(Op op, Register left, Operand right) {
        // TODO Auto-generated method stub
        switch (op) {
        case ADD :
            as.add(right, left);
            break;
        case SUB :
            as.sub(right, left);
            break;
        case MUL :
            as.imul(right, left);
            break;
        case S_DIV :
        case S_MOD :
            as.cltd();
            as.idiv(cx(left.type));
            if (op == Op.S_MOD) {
                as.mov(dx(), left);
            }
            break;
        case BIT_AND :
            as.and(right, left);
            break;
        case BIT_OR :
            as.or(right, left);
            break;
        case BIT_XOR :
            as.xor(right, left);
            break;
        case BIT_LSHIFT :
            as.sal(cl(), left);
            break;
        case BIT_RSHIFT :
            as.shr(cl(), left);
            break;
        case ARITH_RSHIFT :
            as.sar(cl(), left);
            break;
        default :
            as.cmp(right, ax(left.type));
            switch (op) {
            case EQ :
                as.sete(al());
                break;
            case NEQ :
                as.setne(al());
                break;
            case S_GT :
                as.setg(al());
                break;
            case S_GTEQ :
                as.setge(al());
                break;
            case S_LT :
                as.setl(al());
                break;
            case S_LTEQ :
                as.setle(al());
                break;
            case U_GT :
                as.seta(al());
                break;
            case U_GTEQ :
                as.setae(al());
                break;
            case U_LT :
                as.setb(al());
                break;
            case U_LTEQ :
                as.setbe(al());
                break;
            default :
                throw new Error("unknown binary operator: " + op);
            }
            as.movzx(al(), left);
        }
    }

    private boolean doesRequireRegisterOperand(Op op) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Void visit(Call node) {
        // TODO change amd64
    	for (Expr arg : ListUtils.reverse(node.args())) {
    		compile(arg);
    		as.push(ax());
    	}
    	if (node.isStaticCall()) {
    		as.call(node.function().callingSymbol());
    	} else {
    		compile(node.expr());
    		as.callAbsolute(ax());
    	}
    	// >4 bytes arguments are not supported.
    	rewindStack(as, stackSizeFromWordNum(node.numArgs()));
       return null;
    }

    private void rewindStack(AssemblyCode as2, Object stackSizeFromWordNum) {
		// TODO Auto-generated method stub
		
	}

	private Object stackSizeFromWordNum(long numArgs) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
     * leal 4(%ebp), %eax
     */
    @Override
    public Void visit(Addr node) {
        loadAddress(node.entity(), ax());
        return null;
    }

    @Override
    public Void visit(Mem node) {
        compile(node.expr());
        load(mem(ax()), ax(node.type()));
        return null;
    }

    /**
     * movl 4(%ebp), %eax
     */
    @Override
    public Void visit(Var node) {
        loadVariable(node, ax());
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

    private Register cx() {
        return cx(naturalType);
    }

    private Register cl() {
        return cx(Type.INT8);
    }

    private Register cx(Type t) {
        return new Register(RegisterClass.CX, t);
    }

    private Register dx() {
        return dx(naturalType);
    }

    private Register dx(Type t) {
        return new Register(RegisterClass.DX, t);
    }

}
