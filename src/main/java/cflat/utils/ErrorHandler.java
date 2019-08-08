package cflat.utils;

import java.io.*;
/**
 */
public class ErrorHandler {
    protected String programId;
    protected PrintStream stream;
    
    public ErrorHandler(String programid) {
	this.programId = programid;
	stream = System.err;
    }

    public void error(String msg) {
	// TODO
    }
}
