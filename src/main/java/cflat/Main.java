package cflat;

import cflat.parser.Parser;
import cflat.parser.ParseException;
import java.io.*;

public class Main {
    public static void main(String[] args) {
	try {
	    File f = new File(args[0]);
	    Reader reader = new FileReader(f);

	    Parser.parse(reader);
	    System.out.println("parsed");
	} catch(FileNotFoundException e) {
	} catch(ParseException e) {
	    System.out.println(e.getMessage());
	}
    }
}
