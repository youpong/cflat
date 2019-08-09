package cflat.compiler;

import cflat.type.TypeTable;
import cflat.ast.AST;
import cflat.exception.CompileException;
import cflat.exception.SyntaxException;
import cflat.exception.FileException;
import cflat.utils.ErrorHandler;
import cflat.ir.IR;
import cflat.sysdep.x86.AssemblyCode;
import cflat.parser.Parser;
import java.util.List;
import java.io.*;

/**
 *
 */
public class Compiler {
    static final public String ProgramName = "cbc";
    static final public String Version = "0.1";

    static public void main(String[] args) {
	new Compiler(ProgramName).commandMain(args);
    }

    private final ErrorHandler errorHandler;

    public Compiler(String programName) {
	this.errorHandler = new ErrorHandler(programName);
    }

    public void commandMain(String[] args) {
	// TODO:
	Options opts = Options.parse(args);
	if(opts.mode() == CompilerMode.CheckSyntax) {
	    System.exit(checkSyntax(opts) ? 0 : 1);
	}
	try {
	    List<SourceFile> srcs = opts.sourceFiles();
	    build(srcs, opts);
	}
	catch(CompileException ex) {
	    errorHandler.error(ex.getMessage());
	    System.exit(1);
	}
    }

    private boolean checkSyntax(Options opts) {
	boolean failed = false;
	for(SourceFile src : opts.sourceFiles()) {
	    if(isValidSyntax(src.path(), opts)) {
		System.out.println(src.path() + ": Syntax OK");
	    } else {
		System.out.println(src.path() + ": Syntax Error");
		failed = true;
	    }
	}
	return !failed;
    }

    private boolean isValidSyntax(String path, Options opts) {
	try {
	    parseFile(path, opts);
	    return true;
	}
	catch(SyntaxException ex) {
	    return false;
	}
	catch(FileException ex) {
	    errorHandler.error(ex.getMessage());
	    return false;
	}
    }
    
    public void build(List<SourceFile> srcs, Options opts)
	throws CompileException {
	for(SourceFile src : srcs) {
	    compile(src.path(), opts.asmFileNameOf(src), opts);
	    assemble(src.path(), opts.objFileNameOf(src), opts);
	}
	link(opts);
    }

    public void compile(String srcPath, String destPath, Options opts)
	throws CompileException {
	AST ast = parseFile(srcPath, opts);
	TypeTable types = opts.typeTable();
	AST sem = semanticAnalyze(ast, types, opts);
	IR ir = new IRGenerator(errorHandler).generate(sem, types);
	AssemblyCode asm = generateAssembly(ir, opts);
	writeFile(destPath, asm);
    }

    public AST parseFile(String path, Options opts)
	throws SyntaxException, FileException {
	// TODO
	return Parser.parseFile(new File(path), opts.loader(), errorHandler,
			 opts.doesDebugParser());
    }
    public AST semanticAnalyze(AST ast, TypeTable types, Options opts) {
	// TODO
	return null;
    }
    public AssemblyCode generateAssembly(IR ir, Options opts) {
	// TODO
	return null;
    }
    public void writeFile(String destPath, AssemblyCode asm) {
	// TODO
    }
    
    public void assemble(String srcPath, String destPath, Options opts) {
	// TODO
    }

    public void link(Options opts) {
	// TODO
    }
}
