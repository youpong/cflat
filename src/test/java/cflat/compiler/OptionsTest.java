package cflat.compiler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import cflat.exception.OptionParseError;
import java.util.Iterator;

public class OptionsTest {

    //
    // test asmFileNameOf/objFileNameOf
    //

    @Test
    public void xxxFileNameOf00() {
        String[] args = {"-c", "-obar.o", "sample/foo.cb"}; // -opath

        Options opts = Options.parse(args);
        SourceFile sf = opts.sourceFiles().get(0);

        assertEquals(CompilerMode.Assemble, opts.mode());
        assertEquals("foo.s", opts.asmFileNameOf(sf));
        assertEquals("bar.o", opts.objFileNameOf(sf));
    }

    @Test
    public void xxxFileNameOf01() {
        String[] args = {"-c", "sample/foo.cb"};

        Options opts = Options.parse(args);
        SourceFile sf = opts.sourceFiles().get(0);

        assertEquals(CompilerMode.Assemble, opts.mode());
        assertEquals("foo.s", opts.asmFileNameOf(sf));
        assertEquals("foo.o", opts.objFileNameOf(sf));
    }

    @Test
    public void xxxFileNameOf10() {
        String[] args = {"-S", "-o", "bar.s", "sample/foo.cb"}; // -o path

        Options opts = Options.parse(args);
        SourceFile sf = opts.sourceFiles().get(0);

        assertEquals(CompilerMode.Compile, opts.mode());
        assertEquals("bar.s", opts.asmFileNameOf(sf));
        assertEquals("foo.o", opts.objFileNameOf(sf));
    }

    @Test
    public void xxxFileNameOf11() {
        String[] args = {"-S", "sample/foo.cb"};

        Options opts = Options.parse(args);
        SourceFile sf = opts.sourceFiles().get(0);

        assertEquals(CompilerMode.Compile, opts.mode());
        assertEquals("foo.s", opts.asmFileNameOf(sf));
        assertEquals("foo.o", opts.objFileNameOf(sf));
    }

    /**
     * test opt.sourceFiles() 1 file
     */
    @Test
    public void sourceFiles1() {
        String path = "sample/foo.cb";
        String[] args = {path};

        Options opt = Options.parse(args);
        SourceFile f = opt.sourceFiles().get(0);
        assertEquals(path, f.path());
    }

    /**
     * test opt.sourceFiles() 3 files
     */
    @Test
    public void sourceFiles2() {
        String[] args = {"sample/foo.cb", "sample/bar.s", "sample/baz.o"};
        Options opts = Options.parse(args);
        Iterator<SourceFile> ite = opts.sourceFiles().iterator();

        assertEquals("sample/foo.cb", ite.next().path());
        assertEquals("sample/bar.s", ite.next().path());
        assertEquals("sample/baz.o", ite.next().path());
    }

    /**
     * test isXXXRequired0
     */
    @Test
    public void isXXXequired0() {
        String[] args = {"--dump-asm", "sample/foo.cb"};

        Options opts = Options.parse(args);

        assertEquals(false, opts.isAssembleRequired());
        assertEquals(false, opts.isLinkRequired());
    }

    /**
     * test isXXXRequired1
     */
    @Test
    public void isXXXequired1() {
        String[] args = {"-c", "sample/foo.cb"};

        Options opts = Options.parse(args);

        assertEquals(true, opts.isAssembleRequired());
        assertEquals(false, opts.isLinkRequired());
    }

    /**
     * test isXXXRequired2
     */
    @Test
    public void isXXXRequired2() {
        String[] args = {"--link", "sample/foo.cb"};

        Options opts = Options.parse(args);

        assertEquals(true, opts.isAssembleRequired());
        assertEquals(true, opts.isLinkRequired());
    }

    /**
     * Error: exclusive option
     */
    @Test
    public void parseArgs2() {
        String[] args = {"--check-syntax", "--check-syntax"};
        Error e = assertThrows(OptionParseError.class, () -> {
            Options.parse(args);
        });
        assertEquals("--check-syntax option and --check-syntax option is exclusive",
                e.getMessage());
    }

    /**
     * Error: no input file
     */
    @Test
    public void parseArgs3() {
        String[] args = {"--check-syntax"};
        Error e = assertThrows(OptionParseError.class, () -> {
            Options.parse(args);
        });
        assertEquals("no input file", e.getMessage());
    }

    /**
     * Error: missing argument for -o
     */
    @Test
    public void nextArg() {
        String[] args = {"-o"}; // -o requires arg

        Error e = assertThrows(OptionParseError.class, () -> {
            Options.parse(args);
        });
        assertEquals("missing argument for -o", e.getMessage());
    }

    /**
     * Error: -o option requires only 1 input (except linking)
     */
    @Test
    public void only1Input() {
        String[] args = {"-c", "-o", "foo.s", "foo0.cb", "foo1.cb"};

        Error e = assertThrows(OptionParseError.class, () -> {
            Options.parse(args);
        });
        assertEquals("-o option requires only 1 input (except linking)",
                e.getMessage());
    }

    /**
     * Error: unknown file type
     */
    @Test
    public void unknownFileType() {
        String[] args = {"foo.cc"};

        Error e = assertThrows(OptionParseError.class, () -> {
            Options.parse(args);
        });
        assertEquals("unknown file type: foo.cc", e.getMessage());
    }

}
