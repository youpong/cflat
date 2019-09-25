package cflat.ir;

/**
 * 中間表現の演算子
 */
public enum Op {
    /** 加算(+) */
    ADD,
    /** 減算(-) */
    SUB,
    /** 乗算(*) */
    MUL,
    /** 符号付き除算(/) */
    S_DIV,
    /** 符号なし除算(/) */
    U_DIV,
    /** 符号付き剰余(%) */
    S_MOD,
    /** 符号なし剰余(%) */
    U_MOD,
    /** ビット単位の論理積(&amp;) */
    BIT_AND,
    /** ビット単位の論理和(|) */
    BIT_OR,
    /** ビット単位の排他的論理和(^) */
    BIT_XOR,
    /** 左シフト (符号なしシフト &lt;&lt;) */
    BIT_LSHIFT,
    /** 右シフト (符号なしシフト &gt;&gt;) */
    BIT_RSHIFT,
    /** 算術右シフト (符号付きシフト &gt;&gt;) */
    ARITH_RSHIFT,

    /** 比較 (==) */
    EQ,
    /** 比較 (!=) */
    NEQ,
    /** 符号付き数値の比較 (&gt;) */
    S_GT,
    /** 符号付き数値の比較 (&gt;=) */
    S_GTEQ,
    /** 符号付き数値の比較 (&lt;) */
    S_LT,
    /** 符号付き数値の比較 (&lt;=) */
    S_LTEQ,
    /** 符号なし数値の比較 (&gt;) */
    U_GT,
    /** 符号なし数値の比較 (&gt;=) */
    U_GTEQ,
    /** 符号なし数値の比較 (&lt;) */
    U_LT,
    /** 符号なし数値の比較 (&lt;=) */
    U_LTEQ,

    /** 符号反転 (-) */
    UMINUS,
    /** ビット単位の否定 (~) */
    BIT_NOT,
    /** 論理否定 (!) */
    NOT,

    /** 符号付き数値のキャスト */
    S_CAST,
    /** 符号なし数値のキャスト */
    U_CAST;

    static public Op internBinary(String op, boolean isSigned) {
	if (op.equals("+")) {
	    return Op.ADD;
	} else if (op.equals("-")) {
	    return Op.SUB;
	} else if (op.equals("*")) {
	    return Op.MUL;
	} else if (op.equals("/")) {
	    return isSigned ? Op.S_DIV : Op.U_DIV;
	} else if (op.equals("%")) {
	    return isSigned ? Op.S_MOD : Op.U_MOD;
	} else if (op.equals("&")) {
	    return Op.BIT_AND;
	} else if (op.equals("|")) {
	    return Op.BIT_OR;
	} else if (op.equals("^")) {
	    return Op.BIT_XOR;
	} else if (op.equals("<<")) {
	    return Op.BIT_LSHIFT;
	} else if (op.equals(">>")) {
	    return isSigned ? Op.ARITH_RSHIFT : Op.BIT_RSHIFT;
	} else if (op.equals("==")) {
	    return Op.EQ;
	} else if (op.equals("!=")) {
	    return Op.NEQ;
	} else if (op.equals("<")) {
	    return isSigned ? Op.S_LT : Op.U_LT;
	} else if (op.equals("<=")) {
	    return isSigned ? Op.S_LTEQ : Op.U_LTEQ;	    
	} else if (op.equals(">")) {
	    return isSigned ? Op.S_GT : Op.U_GT;	    
	} else if (op.equals(">=")) {
	    return isSigned ? Op.S_GTEQ : Op.U_GTEQ;
	} else {
	    throw new Error("unknown binary op: " + op);
	}
    }
    static public Op internUnary(String op) {
	if (op.equals("+")) {
	    throw new Error("unary+ should not be in IR");
	} else if (op.equals("-")) {
	    return Op.UMINUS;
	} else if (op.equals("~")) {
	    return Op.BIT_NOT;
	} else if (op.equals("!")) {
	    return Op.NOT;
	} else {
	    throw new Error("unkonw unary op: " + op);
	}
    }
}
