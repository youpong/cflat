package cflat.compiler;

import java.io.File;

public class SourceFile implements LdArg {
    static final String EXT_CFLAT_SOURCE = ".cb";
    static final String EXT_ASSEMBLY_SOURCE = ".s";
    static final String EXT_OBJECT_FILE = ".o";
    // ...

    // @formatter:off    
    static final String[] KNOWN_EXTENSIONS = {
	EXT_CFLAT_SOURCE,
	EXT_ASSEMBLY_SOURCE,
	EXT_OBJECT_FILE
    };
    // @formatter:on

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

    boolean isKnownFileType() {
        String ext = extName(originalName);
        for (String e : KNOWN_EXTENSIONS) {
            if (e.equals(ext))
                return true;
        }
        return false;
    }

    // 57
    boolean isCflatSource() {
        return extName(currentName).equals(EXT_CFLAT_SOURCE);
    }

    boolean isAssemblySource() {
        return extName(currentName).equals(EXT_ASSEMBLY_SOURCE);
    }

    // ...

    // 81
    public String asmFileName() {
        return replaceExt(EXT_ASSEMBLY_SOURCE);
    }

    public String objFileName() {
        return replaceExt(EXT_OBJECT_FILE);
    }

    //    String linkedFileName(String newExt)

    private String replaceExt(String ext) {
        return new File(originalName).getName().replaceFirst("\\.[^.]*$", "") + ext;
    }

    private String extName(String path) {
        int idx = path.lastIndexOf(".");
        if (idx < 0)
            return "";
        return path.substring(idx);
    }
}
