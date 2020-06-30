package cflat.ir;

import cflat.asm.Label;
import cflat.ast.Location;
import java.util.*;

/**
 * 多方向ジャンプ (switch)
 */
public class Switch extends Stmt {
    /** 条件式 */
    protected Expr cond;
    /** 比較値とそのジャンプ先の組のリスト */
    protected List<Case> cases;
    /** デフォルトのジャンプ先 */
    protected Label defaultLabel;
    protected Label endLabel;

    public Switch(Location loc, Expr cond, List<Case> cases, Label defaultLabel,
            Label endLabel) {
        super(loc);
        this.cond = cond;
        this.cases = cases;
        this.defaultLabel = defaultLabel;
        this.endLabel = endLabel;
    }

    public Expr cond() {
        return cond;
    }

    public List<Case> cases() {
        return cases;
    }

    public Label defaultLabel() {
        return defaultLabel;
    }
    /*
    public Label endLabel() {
    return endLabel;
    }
    */
    public <S, E> S accept(IRVisitor<S, E> visitor) {
        return visitor.visit(this);
    }

    protected void _dump(Dumper d) {
        d.printMember("cond", cond);
        d.printMembers("cases", cases);
        d.printMember("defaultLabel", defaultLabel);
        d.printMember("endLabel", endLabel);
    }
}
