package cflat.compiler;

import java.util.*;

enum CompilerMode {
    CheckSyntax ("--check-syntax"),
    DumpTokens ("--dump-tokens"),
    DumpAST ("--dump-ast"),
    DumpSemantic ("--dump-semantic"),
    DumpIR ("--dump-ir"),
    DumpAsm ("--dump-asm");

    private final String option;
    
    CompilerMode(String option) {
	this.option = option;
    }

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
    
    public String toOption() {
	return option;
    }
    boolean requires(CompilerMode m) {
	return ordinal() >= m.ordinal();
    }
}
