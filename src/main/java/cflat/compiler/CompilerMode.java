package cflat.compiler;

import java.util.*;

enum CompilerMode {
    CheckSyntax ("--check-syntax"),
    DumpTokens ("--dump-tokens"),
    DumpAST ("--dump-ast"),
    DumpSemantic ("--dump-semantic"),
    DumpIR ("--dump-ir"),
    DumpAsm ("--dump-asm");

    static private Map<String, CompilerMode> modes;
    static {
	modes = new HashMap<String, CompilerMode>();
	modes.put("--check_syntax", CheckSyntax);
	modes.put("--dump-tokens", DumpTokens);
    }

    private final String option;
    
    CompilerMode(String option) {
	this.option = option;
    }
}
