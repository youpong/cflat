/**
 * assemply object class express below.
 * 
 * 1. program
 * 2. operand of instruction
 * 3. literal
 *
 * 1. program
 * Assembly
 *     Comment
 *     Directive
 *     Instruction
 *     Lable
 * AssemblyCode: manage list of Assembly instance.
 *
 * 2. operand of instruction
 * Operand
 *     ImmediateValue
 *     MemoryReference
 *         DirectMemoryReference
 *         IndirectMemoryReference
 *     Register
 *     AbsoluteAddress
 *
 * 3. literal
 * Literal*
 *      IntegerLiteral
 *      Symbol*
 *          BaseSymbol
 *              NamedSymbol
 *              UnnamedSymbol
 *          SuffixedSymbol
 */
@RuntimeAnnotation
package cflat.asm;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
@interface RuntimeAnnotation {
}
