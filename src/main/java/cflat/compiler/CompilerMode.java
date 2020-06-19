package cflat.compiler;

import java.util.*;

// TODO: test
/**
 * enum of compiler modes.
 */
enum CompilerMode {
    CheckSyntax ("--check-syntax"),
    DumpTokens ("--dump-tokens"),
    DumpAST ("--dump-ast"),
    DumpStmt ("--dump-stmt"),
    DumpExpr ("--dump-expr"),
    DumpSemantic ("--dump-semantic"),
    DumpReference ("--dump-reference"),
    DumpIR ("--dump-ir"),
    DumpAsm ("--dump-asm"),
    Assemble ("-c");
    //    Link("--link");

    static public boolean isModeOption(String opt) {
	for(CompilerMode mode : CompilerMode.values()) {
	    if (opt.equals(mode.option))
		return true;
	}
	return false;
    }
    
    static public CompilerMode fromOption(String opt) {
	for(CompilerMode mode : CompilerMode.values()) {
	    if (opt.equals(mode.option))
		return mode;
	}
	throw new Error("must not happen: unknown mode option: " + opt);
    }
    
    private final String option;
    
    CompilerMode(String option) {
	this.option = option;
    }
    
    public String toOption() {
	return option;
    }

    boolean requires(CompilerMode m) {
	return ordinal() >= m.ordinal();
    }
}
