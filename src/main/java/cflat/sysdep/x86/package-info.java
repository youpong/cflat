/**
 * OS/CPU 依存のコードを持つクラス(コードジェネレーター)
 */
@RuntimeAnnotation
package cflat.sysdep.x86;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
@interface RuntimeAnnotation {
}
