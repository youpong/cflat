package cflat.utils;

import cflat.ast.Location;
import java.io.*;

// TODO: implement
/**
 */
public class ErrorHandler {
    protected String programId;
    protected PrintStream stream;
    // TODO: test nError initialize 0?
    protected long nError;
    protected long nWarning;

    public ErrorHandler(String programid) {
        this.programId = programid;
        stream = System.err;
    }

    public void error(Location loc, String msg) {
        error(loc.toString() + ": " + msg);
    }

    public void error(String msg) {
        stream.println(programId + ": error: " + msg);
        nError++;
    }

    public void warn(Location loc, String msg) {
        warn(loc.toString() + ": " + msg);
    }

    public void warn(String msg) {
        stream.println(programId + ": warning: " + msg);
        nWarning++;
    }

    // TODO: test
    public boolean errorOccured() {
        return (nError > 0);
    }
}
