package cflat.compiler;

import cflat.asm.Label;
import cflat.asm.SymbolTable;
import cflat.ast.AST;
import cflat.ast.ASTVisitor;
import cflat.ast.AddressNode;
import cflat.ast.ArefNode;
import cflat.ast.AssignNode;
import cflat.ast.BinaryOpNode;
import cflat.ast.BlockNode;
import cflat.ast.BreakNode;
import cflat.ast.CaseNode;
import cflat.ast.CastNode;
import cflat.ast.CondExprNode;
import cflat.ast.ContinueNode;
import cflat.ast.DereferenceNode;
import cflat.ast.DoWhileNode;
import cflat.ast.ExprNode;
import cflat.ast.ExprStmtNode;
import cflat.ast.ForNode;
import cflat.ast.FuncallNode;
import cflat.ast.GotoNode;
import cflat.ast.IfNode;
import cflat.ast.IntegerLiteralNode;
import cflat.ast.LabelNode;
import cflat.ast.Location;
import cflat.ast.LogicalAndNode;
import cflat.ast.LogicalOrNode;
import cflat.ast.MemberNode;
import cflat.ast.Node;
import cflat.ast.OpAssignNode;
import cflat.ast.PrefixOpNode;
import cflat.ast.PtrMemberNode;
import cflat.ast.ReturnNode;
import cflat.ast.SizeofExprNode;
import cflat.ast.SizeofTypeNode;
import cflat.ast.StmtNode;
import cflat.ast.StringLiteralNode;
import cflat.ast.SuffixOpNode;
import cflat.ast.SwitchNode;
import cflat.ast.UnaryOpNode;
import cflat.ast.VariableNode;
import cflat.ast.WhileNode;
import cflat.entity.DefinedFunction;
import cflat.entity.DefinedVariable;
import cflat.entity.Entity;
import cflat.entity.LocalScope;
import cflat.exception.JumpError;
import cflat.exception.SemanticException;
import cflat.ir.Assign;
import cflat.ir.Bin;
import cflat.ir.CJump;
import cflat.ir.Case;
import cflat.ir.Expr;
import cflat.ir.IR;
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
import cflat.type.Type;
import cflat.type.TypeTable;
import cflat.utils.ErrorHandler;
import java.util.*;

/**
 * 抽象構文木から中間表現へ変換する
 */
public class IRGenerator implements ASTVisitor<Void, Expr> {
    private final TypeTable typeTable;
    private final ErrorHandler errorHandler;

    public IRGenerator(TypeTable typeTable, ErrorHandler errorHandler) {
        this.typeTable = typeTable;
        this.errorHandler = errorHandler;
    }

    // TODO: test
    public IR generate(AST ast) throws SemanticException {
        for (DefinedVariable var : ast.definedVariables()) {
            if (var.hasInitializer()) {
                var.setIR(transformExpr(var.initializer()));
            }
        }
        for (DefinedFunction f : ast.definedFunctions()) {
            f.setIR(compileFunctionBody(f));
        }
        if (errorHandler.errorOccured()) {
            throw new SemanticException("IR generation failed.");
        }
        return ast.ir();
    }

    /** 文を変換した中間表現ノード(Stmt)を蓄積する */
    List<Stmt> stmts;
    /** テンポラリ変数を生成するときに「現在の」スコープを得るために使う */
    LinkedList<LocalScope> scopeStack;
    /** break 文の「現在の」ジャンプ先を表すスタック */
    LinkedList<Label> breakStack;
    /** continue 文の「現在の」ジャンプ先を表すスタック */
    LinkedList<Label> continueStack;
    /** goto 文で使うラベルを記録する */
    Map<String, JumpEntry> jumpMap;

    /** 関数本体の変換 */
    public List<Stmt> compileFunctionBody(DefinedFunction f) {
        stmts = new ArrayList<Stmt>();
        scopeStack = new LinkedList<LocalScope>();
        breakStack = new LinkedList<Label>();
        continueStack = new LinkedList<Label>();
        jumpMap = new HashMap<String, JumpEntry>();
        transformStmt(f.body());
        checkJumpLinks(jumpMap);
        return stmts;
    }

    private void transformStmt(StmtNode node) {
        node.accept(this);
    }

    private int exprNestLevel = 0;

    private Expr transformExpr(ExprNode node) {
        exprNestLevel++;
        Expr e = node.accept(this);
        exprNestLevel--;
        return e;
    }

    private boolean isStatement() {
        return (exprNestLevel == 0);
    }

    private void assign(Location loc, Expr lhs, Expr rhs) {
        stmts.add(new Assign(loc, addressOf(lhs), rhs));
    }

    // TODO: test
    private DefinedVariable tmpVar(Type t) {
        return scopeStack.getLast().allocateTmp(t);
    }

    /** ラベル lab を定義する */
    private void label(Label lab) {
        stmts.add(new LabelStmt(null, lab));
    }

    private void jump(Location loc, Label target) {
        stmts.add(new Jump(loc, target));
    }

    /**
     * 無条件ジャンプ ラベル lab にジャンプ
     */
    private void jump(Label lab) {
        stmts.add(new Jump(null, lab));
    }

    /**
     * 条件付きジャンプ。 条件式 cond が真ならラベル t にジャンプ、偽ならラベル e にジャンプ
     */
    private void cjump(Location loc, Expr cond, Label t, Label e) {
        stmts.add(new CJump(loc, cond, t, e));
    }

    /** ラベル label をスタックに積む */
    private void pushBreak(Label label) {
        breakStack.add(label);
    }

    /** スタックからラベルを一つ降ろす */
    private void popBreak() {
        if (breakStack.isEmpty()) {
            throw new Error("unmatched push/pop for break stack");
        }
        breakStack.removeLast();
    }

    /** スタック先頭のラベルを返す */
    private Label currentBreakTarget() {
        if (breakStack.isEmpty()) {
            throw new JumpError("break from out of loop");
        }
        return breakStack.getLast();
    }

    private void pushContinue(Label label) {
        continueStack.add(label);
    }

    private void popContinue() {
        if (continueStack.isEmpty()) {
            throw new Error("unmatched push/pop for continue stack");
        }
        continueStack.removeLast();
    }

    private Label currentContinueTarget() {
        if (continueStack.isEmpty()) {
            throw new JumpError("continue from out of loop");
        }
        return continueStack.getLast();
    }

    //
    // Statements
    //

    public Void visit(BlockNode node) {
        scopeStack.add(node.scope());
        for (DefinedVariable var : node.variables()) {
            if (var.hasInitializer()) {
                if (var.isPrivate()) {
                    // static variables
                    var.setIR(transformExpr(var.initializer()));
                } else {
                    assign(var.location(), ref(var),
                            transformExpr(var.initializer()));
                }
            }
        }
        for (StmtNode s : node.stmts()) {
            transformStmt(s);
        }
        scopeStack.removeLast();
        return null;
    }

    public Void visit(ExprStmtNode node) {
        // do not use transformStmt here, to receive compiled tree.
        Expr e = node.expr().accept(this);
        if (e != null) {
            errorHandler.warn(node.location(), "useless expression");
        }
        return null;
    }

    public Void visit(IfNode node) {
        Label thenLabel = new Label();
        Label elseLabel = new Label();
        Label endLabel = new Label();
        Expr cond = transformExpr(node.cond());
        if (node.elseBody() == null) {
            cjump(node.location(), cond, thenLabel, endLabel);
            label(thenLabel);
            transformStmt(node.thenBody());
            label(endLabel);
        } else {
            cjump(node.location(), cond, thenLabel, elseLabel);
            label(thenLabel);
            transformStmt(node.thenBody());
            jump(endLabel);
            label(elseLabel);
            transformStmt(node.elseBody());
            label(endLabel);
        }
        return null;
    }

    // TODO: test
    public Void visit(SwitchNode node) {
        List<Case> cases = new ArrayList<Case>();
        Label endLabel = new Label();
        Label defaultLabel = endLabel;

        Expr cond = transformExpr(node.cond());
        for (CaseNode c : node.cases()) {
            if (c.isDefault()) {
                defaultLabel = c.label();
            } else {
                for (ExprNode val : c.values()) {
                    Expr v = transformExpr(val);
                    cases.add(new Case(((Int) v).value(), c.label()));
                }
            }
        }
        stmts.add(new Switch(node.location(), cond, cases, defaultLabel, endLabel));
        pushBreak(endLabel);
        for (CaseNode c : node.cases()) {
            label(c.label());
            transformStmt(c.body());
        }
        popBreak();
        label(endLabel);
        return null;
    }

    public Void visit(CaseNode node) {
        throw new Error("must not happen");
    }

    public Void visit(WhileNode node) {
        Label begLabel = new Label();
        Label bodyLabel = new Label();
        Label endLabel = new Label();

        label(begLabel);
        cjump(node.location(), transformExpr(node.cond()), bodyLabel, endLabel);
        label(bodyLabel);
        pushContinue(begLabel);
        pushBreak(endLabel);
        transformStmt(node.body());
        popBreak();
        popContinue();
        jump(begLabel);
        label(endLabel);
        return null;
    }

    public Void visit(DoWhileNode node) {
        Label begLabel = new Label();
        Label contLabel = new Label(); // before cond (end of body)
        Label endLabel = new Label();

        pushContinue(contLabel);
        pushBreak(endLabel);
        label(begLabel);
        transformStmt(node.body());
        popBreak();
        popContinue();
        label(contLabel);
        cjump(node.location(), transformExpr(node.cond()), begLabel, endLabel);
        label(endLabel);
        return null;
    }

    public Void visit(ForNode node) {
        Label begLabel = new Label();
        Label bodyLabel = new Label();
        Label contLabel = new Label();
        Label endLabel = new Label();

        transformStmt(node.init());
        label(begLabel);
        cjump(node.location(), transformExpr(node.cond()), bodyLabel, endLabel);
        label(bodyLabel);
        pushContinue(contLabel);
        pushBreak(endLabel);
        transformStmt(node.body());
        popBreak();
        popContinue();
        label(contLabel);
        transformStmt(node.incr());
        jump(begLabel);
        label(endLabel);
        return null;
    }

    public Void visit(BreakNode node) {
        try {
            jump(node.location(), currentBreakTarget());
        } catch (JumpError err) {
            error(node, err.getMessage());
        }
        return null;
    }

    public Void visit(ContinueNode node) {
        try {
            jump(node.location(), currentContinueTarget());
        } catch (JumpError err) {
            error(node, err.getMessage());
        }
        return null;
    }

    public Void visit(LabelNode node) {
        try {
            stmts.add(new LabelStmt(node.location(),
                    defineLabel(node.name(), node.location())));
            if (node.stmt() != null) {
                transformStmt(node.stmt());
            }
        } catch (SemanticException ex) {
            error(node, ex.getMessage());
        }
        return null;
    }

    public Void visit(GotoNode node) {
        jump(node.location(), referLabel(node.target()));
        return null;
    }

    public Void visit(ReturnNode node) {
        stmts.add(new Return(node.location(),
                node.expr() == null ? null : transformExpr(node.expr())));
        return null;
    }

    class JumpEntry {
        public Label label;
        public long numRefered;
        public boolean isDefined;
        public Location location;

        public JumpEntry(Label label) {
            this.label = label;
            numRefered = 0;
            isDefined = false;
        }
    }

    private Label defineLabel(String name, Location loc) throws SemanticException {
        JumpEntry ent = getJumpEntry(name);
        if (ent.isDefined) {
            throw new SemanticException(
                    "duplicated jump labels in " + name + "(): " + name);
        }
        ent.isDefined = true;
        ent.location = loc;
        return ent.label;
    }

    private Label referLabel(String name) {
        JumpEntry ent = getJumpEntry(name);
        ent.numRefered++;
        return ent.label;
    }

    private JumpEntry getJumpEntry(String name) {
        JumpEntry ent = jumpMap.get(name);
        if (ent == null) {
            ent = new JumpEntry(new Label());
            jumpMap.put(name, ent);
        }
        return ent;
    }

    private void checkJumpLinks(Map<String, JumpEntry> jumpMap) {
        for (Map.Entry<String, JumpEntry> ent : jumpMap.entrySet()) {
            String labelName = ent.getKey();
            JumpEntry jump = ent.getValue();
            if (!jump.isDefined) {
                errorHandler.error(jump.location, "undefined label: " + labelName);
            }
            if (jump.numRefered == 0) {
                errorHandler.warn(jump.location, "useless label: " + labelName);
            }
        }
    }

    // currentBreakTarget();
    // currentContinueTarget();

    //
    // Expressions (with branches)
    //

    public Expr visit(CondExprNode node) {
        Label thenLabel = new Label();
        Label elseLabel = new Label();
        Label endLabel = new Label();
        DefinedVariable var = tmpVar(node.type());

        Expr cond = transformExpr(node.cond());
        cjump(node.location(), cond, thenLabel, elseLabel);
        label(thenLabel);
        assign(node.thenExpr().location(), ref(var), transformExpr(node.thenExpr()));
        jump(endLabel);
        label(elseLabel);
        assign(node.elseExpr().location(), ref(var), transformExpr(node.elseExpr()));
        jump(endLabel);
        label(endLabel);
        return isStatement() ? null : ref(var);
    }

    // TODO: implement
    public Expr visit(LogicalAndNode node) {
        return null;
    }

    // TODO: implement
    public Expr visit(LogicalOrNode node) {
        return null;
    }

    //
    // Expressions (with side effects)
    //

    // TODO: test
    public Expr visit(AssignNode node) {
        Location lloc = node.lhs().location();
        Location rloc = node.rhs().location();
        if (isStatement()) {
            // Evaluate RHS before LHS
            Expr rhs = transformExpr(node.rhs());
            assign(lloc, transformExpr(node.lhs()), rhs);
            return null;
        } else {
            // lhs = rhs -> tmp = rhs, lhs = tmp, tmp
            DefinedVariable tmp = tmpVar(node.rhs().type());
            assign(rloc, ref(tmp), transformExpr(node.rhs()));
            assign(lloc, transformExpr(node.lhs()), ref(tmp));
            return ref(tmp);
        }
    }

    // TODO: test
    public Expr visit(OpAssignNode node) {
        // Evaluate RHS before LHS.
        Expr rhs = transformExpr(node.rhs());
        Expr lhs = transformExpr(node.lhs());
        Type t = node.lhs().type();
        Op op = Op.internBinary(node.operator(), t.isSigned());
        return transformOpAssign(node.location(), op, t, lhs, rhs);
    }

    // TODO: test
    public Expr visit(PrefixOpNode node) {
        // ++expr -> expr += 1
        Type t = node.expr().type();
        return transformOpAssign(node.location(), binOp(node.operator()), t,
                transformExpr(node.expr()), imm(t, 1));
    }

    public Expr visit(SuffixOpNode node) {
        Expr expr = transformExpr(node.expr());
        Type t = node.expr().type();
        Op op = binOp(node.operator());
        Location loc = node.location();

        if (isStatement()) {
            // expr++; -> expr += 1
            transformOpAssign(loc, op, t, expr, imm(t, 1));
            return null;
        } else if (expr.isVar()) {
            // cont(expr++) -> v = expr; expr = v + 1; cont(v)
            DefinedVariable v = tmpVar(t);
            assign(loc, ref(v), expr);
            assign(loc, expr, bin(op, t, ref(v), imm(t, 1)));
            return ref(v);
        } else {
            // cont(expr++) -> a = &expr; v = *a; *a = *a + 1; cont(v)
            DefinedVariable a = tmpVar(pointerTo(t));
            DefinedVariable v = tmpVar(t);
            assign(loc, ref(a), addressOf(expr));
            assign(loc, ref(v), mem(a));
            assign(loc, mem(a), bin(op, t, mem(a), imm(t, 1)));
            return ref(v);
        }
    }

    // TODO: test
    private Expr transformOpAssign(Location loc, Op op, Type lhsType, Expr lhs,
            Expr rhs) {
        if (lhs.isVar()) {
            // cont(lhs += rhs) -> lhs = lhs + rhs; cont(lhs)
            assign(loc, lhs, bin(op, lhsType, lhs, rhs));
            return isStatement() ? null : lhs;
        } else {
            // cont(lhs += rhs) -> a = &lhs; *a = *a + rhs; cont(*a)
            DefinedVariable a = tmpVar(pointerTo(lhsType));
            assign(loc, ref(a), addressOf(lhs));
            assign(loc, mem(a), bin(op, lhsType, mem(a), rhs));
            return isStatement() ? null : mem(a);
        }
    }

    // TODO: test
    private Bin bin(Op op, Type leftType, Expr left, Expr right) {
        if (isPointerArithmetic(op, leftType)) {
            return new Bin(left.type(), op, left,
                    new Bin(right.type(), Op.MUL, right, ptrBaseSize(leftType)));
        } else {
            return new Bin(left.type(), op, left, right);
        }
    }

    // TODO: implement
    public Expr visit(FuncallNode node) {
        return null;
    }

    //
    // Expressions (no side effects)
    //

    public Expr visit(BinaryOpNode node) {
        Expr right = transformExpr(node.right());
        Expr left = transformExpr(node.left());
        Op op = Op.internBinary(node.operator(), node.type().isSigned());
        Type t = node.type();
        Type r = node.right().type();
        Type l = node.left().type();

        if (isPointerDiff(op, l, r)) {
            // ptr - ptr -> (ptr - ptr) / ptrBaseSize
            Expr tmp = new Bin(asmType(t), op, left, right);
            return new Bin(asmType(t), Op.S_DIV, tmp, ptrBaseSize(l));
        } else if (isPointerArithmetic(op, l)) {
            // ptr + int -> ptr + (int * ptrBaseSize)
            return new Bin(asmType(t), op, left,
                    new Bin(asmType(r), Op.MUL, right, ptrBaseSize(l)));
        } else if (isPointerArithmetic(op, r)) {
            // int + ptr -> (int * ptrBaseSize) + ptr
            return new Bin(asmType(t), op,
                    new Bin(asmType(l), Op.MUL, left, ptrBaseSize(r)), right);
        } else {
            // int + int
            return new Bin(asmType(t), op, left, right);
        }
    }

    public Expr visit(UnaryOpNode node) {
        if (node.operator().equals("+")) {
            // +expr -> expr
            return transformExpr(node.expr());
        } else {
            return new Uni(asmType(node.type()), Op.internUnary(node.operator()),
                    transformExpr(node.expr()));
        }
    }

    // TODO: implementn
    public Expr visit(ArefNode node) {
        return null;
    }

    // TODO: implement
    private Expr transformIndex(ArefNode node) {
        return null;
    }

    public Expr visit(MemberNode node) {
        Expr expr = addressOf(transformExpr(node.expr()));
        Expr offset = ptrdiff(node.offset());
        Expr addr = new Bin(ptr_t(), Op.ADD, expr, offset);
        return node.isLoadable() ? mem(addr, node.type()) : addr;
    }

    public Expr visit(PtrMemberNode node) {
        Expr expr = transformExpr(node.expr());
        Expr offset = ptrdiff(node.offset());
        Expr addr = new Bin(ptr_t(), Op.ADD, expr, offset);
        return node.isLoadable() ? mem(addr, node.type()) : addr;
    }

    public Expr visit(DereferenceNode node) {
        Expr addr = transformExpr(node.expr());
        return node.isLoadable() ? mem(addr, node.type()) : addr;
    }

    public Expr visit(AddressNode node) {
        Expr e = transformExpr(node.expr());
        return node.expr().isLoadable() ? addressOf(e) : e;
    }

    // TODO: implement
    public Expr visit(CastNode node) {
        return null;
    }

    public Expr visit(SizeofExprNode node) {
        return new Int(size_t(), node.expr().allocSize());
    }

    public Expr visit(SizeofTypeNode node) {
        return new Int(size_t(), node.operand().allocSize());
    }

    public Expr visit(VariableNode node) {
        if (node.entity().isConstant()) {
            return transformExpr(node.entity().value());
        }
        Var var = ref(node.entity());
        return node.isLoadable() ? var : addressOf(var);
    }

    public Expr visit(IntegerLiteralNode node) {
        return new Int(asmType(node.type()), node.value());
    }

    public Expr visit(StringLiteralNode node) {
        return new Str(asmType(node.type()), node.entry());
    }

    //
    // Utilities
    //

    private boolean isPointerDiff(Op op, Type l, Type r) {
        return op == Op.SUB && l.isPointer() && r.isPointer();
    }

    private boolean isPointerArithmetic(Op op, Type operandType) {
        switch (op) {
        case ADD :
        case SUB :
            return operandType.isPointer();
        default :
            return false;
        }
    }

    private Expr ptrBaseSize(Type t) {
        return new Int(ptrdiff_t(), t.baseType().size());
    }

    // unary ops -> binary ops
    private Op binOp(String uniOp) {
        return uniOp.equals("++") ? Op.ADD : Op.SUB;
    }

    /**
     * 右辺値を左辺値に変換する
     * <ol>
     * <li>右辺値が Var ノードなら Addr ノードに変換する</li>
     * <li>右辺値が Mem ノードなら Mem ノードを取り外す</li>
     * <li>それ以外の場合は致命的なエラー（コンパイラのバグ）</li>
     * </ol>
     */
    private Expr addressOf(Expr expr) {
        return expr.addressNode(ptr_t());
    }

    private Var ref(Entity ent) {
        return new Var(varType(ent.type()), ent);
    }

    private Mem mem(Entity ent) {
        return new Mem(asmType(ent.type().baseType()), ref(ent));
    }

    private Mem mem(Expr expr, Type t) {
        return new Mem(asmType(t), expr);
    }

    private Int ptrdiff(long n) {
        return new Int(ptrdiff_t(), n);
    }

    //
    private Int imm(Type operandType, long n) {
        if (operandType.isPointer()) {
            return new Int(ptrdiff_t(), n);
        } else {
            return new Int(int_t(), n);
        }
    }

    private Type pointerTo(Type t) {
        return typeTable.pointerTo(t);
    }

    private cflat.asm.Type asmType(Type t) {
        if (t.isVoid())
            return int_t();
        return cflat.asm.Type.get(t.size());
    }

    private cflat.asm.Type varType(Type t) {
        if (!t.isScalar()) {
            return null;
        }
        return cflat.asm.Type.get(t.size());
    }

    private cflat.asm.Type int_t() {
        return cflat.asm.Type.get((int) typeTable.intSize());
    }

    private cflat.asm.Type size_t() {
        return cflat.asm.Type.get((int) typeTable.longSize());
    }

    private cflat.asm.Type ptr_t() {
        return cflat.asm.Type.get((int) typeTable.pointerSize());
    }

    private cflat.asm.Type ptrdiff_t() {
        return cflat.asm.Type.get((int) typeTable.longSize());
    }

    private void error(Node n, String msg) {
        errorHandler.error(n.location(), msg);
    }
}
