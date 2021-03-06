package cflat.ast;

import cflat.parser.Token;

public class Location {
    protected String sourceName;
    protected CflatToken token;

    public Location(String sourceName, Token token) {
        this(sourceName, new CflatToken(token));
    }

    public Location(String sourceName, CflatToken token) {
        this.sourceName = sourceName;
        this.token = token;
    }

    public String sourceName() {
        return sourceName;
    }

    public CflatToken token() {
        return token;
    }

    // ...

    public String line() {
        return token.includedLine();
    }

    public String numberedLine() {
        return "Line " + token.lineno() + ": " + line();
    }

    public String toString() {
        return sourceName + ":" + token.lineno();
    }
}
