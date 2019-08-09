package cflat.ast;

import java.io.*;
import cflat.utils.TextUtils;

public class Dumper {
    protected int nIndent;
    protected PrintStream stream;
    
    public Dumper(PrintStream s) {
	this.stream = s;
	this.nIndent = 0;
    }
    
    public void printClass(Object obj, Location loc) {
	printIndent();
	stream.println("<<" + obj.getClass().getSimpleName() +
		       ">> (" + loc + ")");
    }

    static final protected String indentString = "    ";
    
    protected void printIndent() {
	for(int n = nIndent; n > 0; n--){    
	    stream.print(indentString);
	}
    }
    
    public void printMember(String name, String str, boolean isResolved) {
	printPair(name, TextUtils.dumpString(str)
		  + (isResolved ? "(resolved)" : ""));
    }
    public void printMember(String name, String str) {
	printMember(name, str, false);
    }
    public void printPair(String name, String value) {
	printIndent();
	stream.println(name + ": " + value);
    }
    public void printMember(String name, Dumpable n) {
	printIndent();
	if(n == null)
	    stream.println(name + ": null");
	else {
	    stream.println(name + ":");
	    indent();
	    n.dump(this);
	    unindent();
	}
    }

    protected void indent() { nIndent++; }
    protected void unindent() { nIndent--; }
}

