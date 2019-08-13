package cflat.compiler;

import cflat.type.TypeTable;
import cflat.exception.OptionParseError;
import cflat.parser.LibraryLoader;
import cflat.sysdep.Platform;
import cflat.sysdep.X86Linux;
import java.util.*;
import java.io.*;

public class Options {

    private CompilerMode mode;
    private Platform platform = new X86Linux();
    
    private LibraryLoader loader = new LibraryLoader();
    private boolean debugParser = false;

    private List<LdArg> ldArgs;
    private List<SourceFile> sourceFiles;

    
    public static Options parse(String[] args) {
	Options opts = new Options();
	opts.parseArgs(args);
	return opts;
    }

    boolean doesDebugParser() {
	return this.debugParser;
    }
    
    List<SourceFile> sourceFiles() {
	return sourceFiles;
    }

    String asmFileNameOf(SourceFile src) {
	// TODO: CompileMode.Compile
	return src.asmFileName();
    }
    String objFileNameOf(SourceFile src) {
	// TODO: CompileMode.Assemble
	return src.objFileName();
    }
    // TODO: test
    TypeTable typeTable() {
	return platform.typeTable();
    }
    
    void parseArgs(String[] origArgs) {
	// TODO: set sourceFiles
	// sourceFiles = new ArrayList<SourceFile>();
	ldArgs = new ArrayList<LdArg>();
	ListIterator<String> args = Arrays.asList(origArgs).listIterator();
	while(args.hasNext()) {
	    String arg = args.next();
	    if  (arg.equals("--")) {
		// "--" stops command line processing
		break;
	    }
	    else if(arg.startsWith("-")) {
		if (CompilerMode.isModeOption(arg)) {
		    if (mode != null) {
			parseError(mode.toOption() + " option and "
				   + arg + " option is exclusive");
		    }
		    mode = CompilerMode.fromOption(arg);
		}
		else if (arg.equals("--debug-parser")) {
		    debugParser = true;
		}
		else if (arg.equals("--help")) {
		    printUsage(System.out);
		    System.exit(0);
		}
		else {
		    parseError("unknown option: " + arg);
		}
	    }
	    else {
		ldArgs.add(new SourceFile(arg));
	    }
	}
	// args has more arguments when "--" is appeard.
	while(args.hasNext()) {
	    ldArgs.add(new SourceFile(args.next()));
	}

	sourceFiles = selectSourceFiles(ldArgs);
	if (sourceFiles.isEmpty()) {
	    parseError("no input file");
	}
    }

    private void parseError(String msg) {
	throw new OptionParseError(msg);
    }

    private List<SourceFile> selectSourceFiles(List<LdArg> args) {
	List<SourceFile> result = new ArrayList<SourceFile>();
	for(LdArg arg : args) {
	    if(arg.isSourceFile()) {
		result.add((SourceFile)arg);
	    }
	}
	return result;	
    }

    public CompilerMode mode() {
	return mode;
    }
    LibraryLoader loader() {
	return loader;
    }
    
    void printUsage(PrintStream out) {
	String msg = "Usage: cbc [options] file...\n" +
	    "Global Options:\n" +
	    "  --check-syntax  Check syntax and quit.\n" +
	    "  --dump-tokens   Dumps tokens and quit.\n" +
	    "  --dump-ast      Dumps AST and quit.\n" +
	    "  --dump-semantic Dumps AST after semantic checks and quit.\n" +
	    "  --dump-ir       Dumps IR and quit.\n" +
	    "  --version       Shows compiler version.";
	out.println(msg);
    }
}
