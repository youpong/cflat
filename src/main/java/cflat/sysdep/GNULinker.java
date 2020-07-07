package cflat.sysdep;

import cflat.exception.IPCException;
import cflat.utils.ErrorHandler;
import cflat.utils.CommandUtils;
import java.util.ArrayList;
import java.util.List;

class GNULinker implements Linker {
    // @formatter:off
    static final private String LINKER              = "/usr/bin/ld";
    static final private String DYNAMIC_LINKER      = "/lib/ld-linux.so.2";
    static final private String C_RUNTIME_INIT      = "/usr/lib32/crti.o";
    static final private String C_RUNTIME_START     = "/usr/lib32/crt1.o";
    static final private String C_RUNTIME_START_PIE = "/usr/lib/Scrt1.o";
    static final private String C_RUNTIME_FINI      = "/usr/lib32/crtn.o";
    // @formatter:on
    ErrorHandler errorHandler;

    GNULinker(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void generateExecutable(List<String> args, String destPath,
            LinkerOptions opts) throws IPCException {
        List<String> cmd = new ArrayList<String>();
        cmd.add(LINKER);
        /* 
        * LD supports these emulation mode
        *  * elf_x86_64
         *  * elf32_x86_64
         *  * elf_i386
         *  * elf_iamcu
         *  * elf_l1om
         *  * elf_k1om
         *  * i386pepn
         *  * i386pe
        */
        cmd.add("-melf_i386");
        cmd.add("-dynamic-linker");
        cmd.add(DYNAMIC_LINKER);
        if (opts.generatingPIE) {
            cmd.add("-pie");
        }
        if (!opts.noStartFiles) {
            cmd.add(opts.generatingPIE ? C_RUNTIME_START_PIE : C_RUNTIME_START);
            cmd.add(C_RUNTIME_INIT);
        }
        cmd.addAll(args);
        if (!opts.noDefaultLibs) {
            cmd.add("-lc");
            //cmd.add("-lcbc");
        }
        if (!opts.noStartFiles) {
            cmd.add(C_RUNTIME_FINI);
        }
        cmd.add("-o");
        cmd.add(destPath);
        CommandUtils.invoke(cmd, errorHandler, opts.verbose);
    }

    public void generateSharedLibrary(List<String> args, String destPath,
            LinkerOptions opts) throws IPCException {
        List<String> cmd = new ArrayList<String>();
        cmd.add(LINKER);
        cmd.add("-shared");
        if (!opts.noStartFiles) {
            cmd.add(C_RUNTIME_INIT);
        }
        cmd.addAll(args);
        if (!opts.noDefaultLibs) {
            cmd.add("-lc");
            cmd.add("-lcbc");
        }
        if (!opts.noStartFiles) {
            cmd.add(C_RUNTIME_FINI);
        }
        cmd.add("-o");
        cmd.add(destPath);
        CommandUtils.invoke(cmd, errorHandler, opts.verbose);
    }
}
