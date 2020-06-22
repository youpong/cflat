package cflat.compiler;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import cflat.exception.OptionParseError;

public class OptionsTest {
    @Test
    public void testParseArgs() {
        String path = "sample/foo.cb";
        String[] args = { path };
        Options opt = Options.parse(args);
        SourceFile f = opt.sourceFiles().get(0);
        assertEquals(path, f.path());
    }

    @Test
    public void testParseArgs2() {
        String[] args = { "--check-syntax", "--check-syntax" };
        assertThrows(OptionParseError.class, () -> {
            Options.parse(args);
        });
    }

    @Test
    public void testParseArgs3() {
        String[] args = { "--check-syntax" };
        Error e = assertThrows(OptionParseError.class, () -> {
            Options.parse(args);
        });
    }
}
