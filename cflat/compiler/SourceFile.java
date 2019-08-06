package cflat.compiler;

public class SourceFile implements LdArg {
    String currentName;
    
    String path() {
	return currentName;
    }
    public String toString() {
	return currentName;
    }
    public boolean isSourceFile() {
	return true;
    }
    public String asmFileName() {
	// TODO
	return null;
    }
    public String objFileName() {
	// TODO
	return null;
    }
}
