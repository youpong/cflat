package cflat.compiler;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import cflat.exception.OptionParseError;

public class OptionsTest {
    /**
     * test opt.sourceFiles()
     */
    @Test
    public void testParseArgs() {
        String path = "sample/foo.cb";
        String[] args = {path};

        Options opt = Options.parse(args);
        SourceFile f = opt.sourceFiles().get(0);
        assertEquals(path, f.path());
    }

    /**
     * test isXXXRequired0
     */
    @Test
    public void testXXXRequired0() {
        String[] args = {"--dump-asm", "sample/foo.cb"};

        Options opts = Options.parse(args);

        assertEquals(false, opts.isAssembleRequired());
        assertEquals(false, opts.isLinkRequired());
    }

    /**
     * test isXXXRequired1
     */
    @Test
    public void testXXXRequired1() {
        String[] args = {"-c", "sample/foo.cb"};

        Options opts = Options.parse(args);

        assertEquals(true, opts.isAssembleRequired());
        assertEquals(false, opts.isLinkRequired());
    }

    /**
     * test isXXXRequired2
     */
    @Test
    public void testXXXRequired2() {
        String[] args = {"--link", "sample/foo.cb"};

        Options opts = Options.parse(args);

        assertEquals(true, opts.isAssembleRequired());
        assertEquals(true, opts.isLinkRequired());
    }

    /**
     * Error: exclusive option
     */
    @Test
    public void testParseArgs2() {
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
    public void testParseArgs3() {
        String[] args = {"--check-syntax"};
        Error e = assertThrows(OptionParseError.class, () -> {
            Options.parse(args);
        });
        assertEquals("no input file", e.getMessage());
    }
}
