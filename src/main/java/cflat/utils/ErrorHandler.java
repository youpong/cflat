package cflat.utils;

import java.io.*;
import cflat.ast.Location;

// TODO: implement
/**
 */
public class ErrorHandler {
    protected String programId;
    protected PrintStream stream;
    // TODO: test nError initialize 0?
    protected long nError;

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

    // TODO: test
    public boolean errorOccured() {
	return (nError > 0);
    }
}
