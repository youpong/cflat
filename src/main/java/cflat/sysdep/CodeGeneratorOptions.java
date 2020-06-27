package cflat.sysdep;

public class CodeGeneratorOptions {
    protected int optimizeLevel;

    // ...

    protected boolean verboseAsm;

    // ...

    public CodeGeneratorOptions() {
        this.verboseAsm = false;
    }

    // ...

    public int optimizeLevel() {
        return optimizeLevel;
    }

    /*
     * public void generateVerboseAsm() {
     * this.verboseAsm = true;
     * }
     */

    public boolean isVerboseAsm() {
        return verboseAsm;
    }

    // ...
}
