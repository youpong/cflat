package cflat.compiler;

import cflat.utils.ErrorHandler;
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
    }
}
