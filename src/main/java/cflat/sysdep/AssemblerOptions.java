package cflat.sysdep;

import java.util.ArrayList;
import java.util.List;

public class AssemblerOptions {
    public boolean verbose = false;
    List<String> args = new ArrayList<String>();

    public void addArg(String arg) {
        args.add(arg);
    }
}
