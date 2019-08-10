package cflat.ast;

import cflat.parser.Token;
import java.util.*;

public class CflatToken implements Iterable<CflatToken> {
    protected Token token;
    protected boolean isSpecial;
    
    public CflatToken(Token token) {
	this(token, false);
    }
    public CflatToken(Token token, boolean isSpecial) {
	this.token = token;
	this.isSpecial = isSpecial;
    }
    public Iterator<CflatToken> iterator() {
	return buildTokenList(token, false).iterator();
    }

    // TODO: test
    protected List<CflatToken> buildTokenList(Token first,
					      boolean rejectFirstSpecials) {
	List<CflatToken> result = new ArrayList<CflatToken>();
	boolean rejectSpecials = rejectFirstSpecials;
	for(Token t = first; t != null; t = t.next) {
	    if(t.specialToken != null && !rejectSpecials) {
		Token s = specialTokenHead(t.specialToken);
		for(; s != null; s = s.next) {
		    result.add(new CflatToken(s));
		}
	    }
	    result.add(new CflatToken(t));
	    rejectSpecials = false;
	}
	return result;
    }
    // TODO: test
    protected Token specialTokenHead(Token firstSpecial) {
	Token s = firstSpecial;
	while(s.specialToken != null) {
	    s = s.specialToken;
	}
	return s;
    }
}
