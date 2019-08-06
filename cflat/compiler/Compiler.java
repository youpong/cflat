package cflat.compiler;

import cflat.type.TypeTable;
import cflat.ast.AST;
import cflat.exception.CompileException;
import cflat.utils.ErrorHandler;
import cflat.ir.IR;
import cflat.sysdep.x86.AssemblyCode;
import java.util.List;

/**
 *
 */
public class Compiler {
    static final public String ProgramName = "cbc";
    static final public String Version = "1.0.0";

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
	try {
	    List<SourceFile> srcs = opts.sourceFiles();
	    build(srcs, opts);
	}
	catch(CompileException ex) {
	    errorHandler.error(ex.getMessage());
	    System.exit(1);
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

    public AST parseFile(String srcPath, Options opts) {
	// TODO
	return null;
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
