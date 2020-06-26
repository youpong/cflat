/**
 * cbc は c flat 言語のコンパイラです。 c flat は、 C 言語から瑣末な部分や実装しにくい
 * 機能、混乱しやすい機能を省き 実装が明快になるように単純化した言語です。
 */
@RuntimeAnnotation
package cflat;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
@interface RuntimeAnnotation {
}
