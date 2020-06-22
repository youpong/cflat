package cflat.sysdep;

public class CodeGeneratorOptions {
    protected boolean verboseAsm;

    public CodeGeneratorOptions() {
        this.verboseAsm = false;
    }

    public boolean isVerboseAsm() {
        return verboseAsm;
    }
}
