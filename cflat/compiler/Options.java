package cflat.compiler;

import cflat.type.TypeTable;
import java.util.List;

public class Options {
    List<SourceFile> sourceFiles;
    
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
    }
}
