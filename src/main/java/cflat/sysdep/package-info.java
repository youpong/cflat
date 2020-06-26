/**
 * OS 依存のコードを持つクラス(assembler and linker)
 */
@RuntimeAnnotation
package cflat.sysdep;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
@interface RuntimeAnnotation {
}
