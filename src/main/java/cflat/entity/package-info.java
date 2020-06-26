/**
 * 関数や変数などの実体を表現するクラス
 */
@RuntimeAnnotation
package cflat.entity;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
@interface RuntimeAnnotation {
}
