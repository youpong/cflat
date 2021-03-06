package cflat.compiler;

import cflat.exception.OptionParseError;
import cflat.parser.LibraryLoader;
import cflat.sysdep.Assembler;
import cflat.sysdep.AssemblerOptions;
import cflat.sysdep.CodeGenerator;
import cflat.sysdep.CodeGeneratorOptions;
import cflat.sysdep.Linker;
import cflat.sysdep.LinkerOptions;
import cflat.sysdep.Platform;
import cflat.sysdep.X86Linux;
import cflat.type.TypeTable;
import cflat.utils.ErrorHandler;
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
    private String outputFileName;
    private boolean verbose = false;
    private LibraryLoader loader = new LibraryLoader();
    private boolean debugParser = false;
    private CodeGeneratorOptions genOptions = new CodeGeneratorOptions();
    private AssemblerOptions asOptions = new AssemblerOptions();
    private LinkerOptions ldOptions = new LinkerOptions();
    private List<LdArg> ldArgs;
    private List<SourceFile> sourceFiles;

    CompilerMode mode() {
        return mode;
    }

    boolean isAssembleRequired() {
        return mode.requires(CompilerMode.Assemble);
    }

    boolean isLinkRequired() {
        return mode.requires(CompilerMode.Link);
    }

    List<SourceFile> sourceFiles() {
        return sourceFiles;
    }

    String asmFileNameOf(SourceFile src) {
        if (outputFileName != null && mode == CompilerMode.Compile) {
            return outputFileName;
        }
        return src.asmFileName();
    }

    String objFileNameOf(SourceFile src) {
        if (outputFileName != null && mode == CompilerMode.Assemble) {
            return outputFileName;
        }
        return src.objFileName();
    }

    String exeFileName() {
        return linkedFileName("");
    }

    String soFileName() {
        return linkedFileName(".so");
    }

    static private final String DEFAULT_LINKER_OUTPUT = "a.out";

    private String linkedFileName(String newExt) {
        if (outputFileName != null) {
            return outputFileName;
        }
        if (sourceFiles.size() == 1) {
            return sourceFiles.get(0).linkedFileName(newExt);
        } else {
            return DEFAULT_LINKER_OUTPUT;
        }
    }

    /*
    String outputFileName() {
    return this.outputFileName;
    }
    */

    boolean isVerboseMode() {
        return this.verbose;
    }

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

    CodeGenerator codeGenerator(ErrorHandler h) {
        return platform.codeGenerator(genOptions, h);
    }

    Assembler assembler(ErrorHandler h) {
        return platform.assembler(h);
    }

    AssemblerOptions asOptions() {
        return asOptions;
    }

    Linker linker(ErrorHandler h) {
        return platform.linker(h);
    }

    LinkerOptions ldOptions() {
        return ldOptions;
    }

    List<String> ldArgs() {
        List<String> result = new ArrayList<String>();
        for (LdArg arg : ldArgs) {
            result.add(arg.toString());
        }
        return result;
    }

    boolean isGeneratingSharedLibrary() {
        return ldOptions.generatingSharedLibrary;
    }

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
                } else if (arg.startsWith("-o")) {
                    outputFileName = getOptArg(arg, args);
                } else if (arg.equals("-fverbose-asm")
                        || arg.equals("--verbose-asm")) {
                    genOptions.generateVerboseAsm();
                } else if (arg.equals("-v")) {
                    verbose = true;
                    asOptions.verbose = true;
                    ldOptions.verbose = true;
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

        if (mode == null) {
            mode = CompilerMode.Link;
        }

        sourceFiles = selectSourceFiles(ldArgs);
        if (sourceFiles.isEmpty()) {
            parseError("no input file");
        }

        for (SourceFile src : sourceFiles) {
            if (!src.isKnownFileType()) {
                parseError("unknown file type: " + src.path());
            }
        }
        if (outputFileName != null && sourceFiles.size() > 1 && !isLinkRequired()) {
            parseError("-o option requires only 1 input (except linking)");
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

    private String getOptArg(String opt, ListIterator<String> args) {
        String path = opt.substring(2);
        if (path.length() != 0) { // -Ipath
            return path;
        } else { // -I path
            return nextArg(opt, args);
        }
    }

    private String nextArg(String opt, ListIterator<String> args) {
        if (!args.hasNext()) {
            parseError("missing argument for " + opt);
        }
        return args.next();
    }

    //    private List<String> parseCommaSeparatedOptions(String opt)

    void printUsage(PrintStream out) {
    // @formatter:off
        String msg = "Usage: cbc [options] file...\n"
                   + "Global Options:\n"
                   + "  --check-syntax  Check syntax and quit.\n"
                   + "  --dump-tokens   Dumps tokens and quit.\n"
                   + "  --dump-ast      Dumps AST and quit.\n"
                   + "  --dump-semantic Dumps AST after semantic checks and quit.\n"
                   + "  --dump-ir       Dumps IR and quit.\n"
                   + "  --dump-asm      Dumps AssemblyCode and quit.\n"
                   + "  --print-asm     Prints assemblu code and quit.\n"
                   + "  -S              Generates an assembly file and quit.\n"
                   + "  -o PATH         Places output in file PATH.\n"
                   + "  -v              Turn on verbose mode.\n"	    
                // + "  --version       Shows compiler version.\n"
                   + "  --help          Prints this message and quit.\n"
                   + "\n"
	    //   + "Optimization Option:\n"
	    //   + "Parser Optons:\n"
                   + "Code Generator Options:\n"
                   + "  -fverbose-asm   Generate assembly with verbose comments.\n"
	    //   + "Assembler Optoins:\n"
	    //   + "Linker Options:\n"
	    ;
    // @formatter:on
        out.println(msg);
    }
}
