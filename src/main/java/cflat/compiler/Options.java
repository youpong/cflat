package cflat.compiler;

import cflat.exception.OptionParseError;
import cflat.parser.LibraryLoader;
import cflat.sysdep.Platform;
import cflat.sysdep.X86Linux;
import cflat.type.TypeTable;
import java.io.*;
import java.util.*;

public class Options {

    static Options parse(String[] args) {
        Options opts = new Options();
        opts.parseArgs(args);
        return opts;
    }

    private CompilerMode mode;
    private Platform platform = new X86Linux();
    // private String outputFileName;
    // private boolean verbose = false;
    private LibraryLoader loader = new LibraryLoader();
    private boolean debugParser = false;
    // private CodeGeneratorOptions genOptions = new CodeGeneratorOptions();
    // private AssemblerOptions asOptions = new AssemblerOptions();
    // private LinkerOptions ldOptions = new LinkerOptions();
    private List<LdArg> ldArgs;
    private List<SourceFile> sourceFiles;

    CompilerMode mode() {
        return mode;
    }

    boolean isAssembleRequired() {
        return mode.requires(CompilerMode.Assemble);
    }

    /** returns false always now */
    boolean isLinkRequired() {
        return false;
        // return mode.requires(CompilerMode.Link);
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

    // ...

    // 93
    boolean doesDebugParser() {
        return this.debugParser;
    }

    LibraryLoader loader() {
        return this.loader;
    }

    // TODO: test
    TypeTable typeTable() {
        return platform.typeTable();
    }

    // ...

    // 137
    void parseArgs(String[] origArgs) {
        ldArgs = new ArrayList<LdArg>();
        ListIterator<String> args = Arrays.asList(origArgs).listIterator();
        while (args.hasNext()) {
            String arg = args.next();
            if (arg.equals("--")) {
                // "--" stops command line processing
                break;
            } else if (arg.startsWith("-")) {
                if (CompilerMode.isModeOption(arg)) {
                    if (mode != null) {
                        parseError(mode.toOption() + " option and " + arg
                                + " option is exclusive");
                    }
                    mode = CompilerMode.fromOption(arg);
                } else if (arg.equals("--debug-parser")) {
                    debugParser = true;
                } else if (arg.equals("--help")) {
                    printUsage(System.out);
                    System.exit(0);
                } else {
                    parseError("unknown option: " + arg);
                }
            } else {
                ldArgs.add(new SourceFile(arg));
            }
        }
        // args has more arguments when "--" is appeard.
        while (args.hasNext()) {
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

    /*
     * private void addLdArg(String arg) { ldArgs.ad(new LdOption(arg)); }
     */

    private List<SourceFile> selectSourceFiles(List<LdArg> args) {
        List<SourceFile> result = new ArrayList<SourceFile>();
        for (LdArg arg : args) {
            if (arg.isSourceFile()) {
                result.add((SourceFile) arg);
            }
        }
        return result;
    }

    void printUsage(PrintStream out) {
        String msg = "Usage: cbc [options] file...\n" + "Global Options:\n"
                + "  --check-syntax  Check syntax and quit.\n"
                + "  --dump-tokens   Dumps tokens and quit.\n"
                + "  --dump-ast      Dumps AST and quit.\n"
                + "  --dump-semantic Dumps AST after semantic checks and quit.\n"
                + "  --dump-ir       Dumps IR and quit.\n"
                + "  --dump-asm      Dumps AssemblyCode and quit.\n"
                + "  --version       Shows compiler version.";
        out.println(msg);
    }
}
