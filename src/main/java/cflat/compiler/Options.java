package cflat.compiler;

import cflat.type.TypeTable;
import cflat.exception.OptionParseError;
import java.util.*;
import java.io.*;

public class Options {

    private List<LdArg> ldArgs;
    private List<SourceFile> sourceFiles;
    private CompilerMode mode;
    private boolean debugParser = false;
    
    public static Options parse(String[] args) {
	Options opts = new Options();
	opts.parseArgs(args);
	return opts;
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
    TypeTable typeTable() {
	// TODO:
	return null;
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
    
    void printUsage(PrintStream out) {
	out.println("Usage: cbc [options] file...");
	out.println("Global Options:");
	out.println("  --check-syntax  Check syntax and quit.");
	out.println("  --dump-tokens   Dumps tokens and quit.");
	out.println("  --dump-ast      Dumps AST and quit.");
	out.println("  --dump-semantic Dump AST after semantic checks and quit.");
	out.println("  --dump-ir       Dumps IR and quit.");
	out.println("  --version       Shows compiler version.");
    }
}
