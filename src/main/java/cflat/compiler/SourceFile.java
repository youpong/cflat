package cflat.compiler;

public class SourceFile implements LdArg {
    private String currentName;
    private final String originalName;

    SourceFile(String name) {
	this.originalName = name;
	this.currentName = name;
    }
    
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
