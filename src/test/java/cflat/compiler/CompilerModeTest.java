package cflat.compiler;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CompilerModeTest {
    @Test
    public void isModeOption() {
        CompilerMode m = CompilerMode.CheckSyntax;
        assertTrue(CompilerMode.isModeOption(m.toOption()));
        assertTrue(!CompilerMode.isModeOption(""));
    }

    @Test
    public void fromOption() {
        CompilerMode m = CompilerMode.CheckSyntax;
        assertEquals("--check-syntax", m.toOption());
    }

    @Test
    public void requires() {
        CompilerMode m0 = CompilerMode.CheckSyntax;
        CompilerMode m1 = CompilerMode.DumpTokens;

        assertTrue(m0.requires(m0));
        assertTrue(!m0.requires(m1));
        assertTrue(true);
    }
}
