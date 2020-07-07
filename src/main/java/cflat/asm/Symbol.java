package cflat.asm;

/**
 * Symbol
 */
public interface Symbol extends Literal {
    public String name();
    //public String toString();
    public String dump();
}
