package cflat.sysdep;

public class CodeGeneratorOptions {
    protected int optimizeLevel;
    protected boolean generatePIC;
    protected boolean generatePIE;
    protected boolean verboseAsm;

    // ...

    public CodeGeneratorOptions() {
        optimizeLevel = 0;
        generatePIC = false;
        generatePIE = false;
        verboseAsm = false;
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

    public boolean isPositionIndependent() {
        return generatePIC || generatePIE;
    }

    // ...

    public boolean isPIERequired() {
        return generatePIE;
    }
}
