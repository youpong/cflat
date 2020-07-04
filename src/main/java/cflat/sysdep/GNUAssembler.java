package cflat.sysdep;

import cflat.exception.IPCException;
import cflat.utils.CommandUtils;
import cflat.utils.ErrorHandler;
import java.util.ArrayList;
import java.util.List;

class GNUAssembler implements Assembler {
    ErrorHandler errorHandler;

    GNUAssembler(ErrorHandler h) {
        this.errorHandler = h;
    }

    public void assemble(String srcPath, String destPath, AssemblerOptions opts)
            throws IPCException {
        List<String> cmd = new ArrayList<String>();

        cmd.add("as");
        cmd.addAll(opts.args);
        cmd.add("-o");
        cmd.add(destPath);
        cmd.add(srcPath);
        CommandUtils.invoke(cmd, errorHandler, opts.verbose);
    }
}
