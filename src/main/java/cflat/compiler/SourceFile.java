package cflat.compiler;

public class SourceFile implements LdArg {
    static final String EXT_CFLAT_SOURCE = ".cb";
    static final String EXT_ASSEMBLY_SOURCE = ".s";

    // ...

    private final String originalName;
    private String currentName;

    SourceFile(String name) {
        this.originalName = name;
        this.currentName = name;
    }

    public boolean isSourceFile() {
        return true;
    }

    public String toString() {
        return currentName;
    }

    String path() {
        return currentName;
    }

    /*
     * String currentName() { return currentName; }
     */
    void setCurrentName(String name) {
        this.currentName = name;
    }

    // ...

    // 57
    boolean isCflatSource() {
        return extName(currentName).equals(EXT_CFLAT_SOURCE);
    }

    boolean isAssemblySource() {
        return extName(currentName).equals(EXT_ASSEMBLY_SOURCE);
    }

    // 81
    public String asmFileName() {
        // TODO
        return null;
    }

    public String objFileName() {
        // TODO
        return null;
    }

    // ...

    private String extName(String path) {
        int idx = path.lastIndexOf(".");
        if (idx < 0)
            return "";
        return path.substring(idx);
    }
}
