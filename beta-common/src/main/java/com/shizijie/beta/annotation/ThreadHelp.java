package com.shizijie.beta.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author shizijie
 * @version 2018-12-05 下午8:09
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ThreadHelp {
    String value() default "ThreadHelper";
    boolean log() default true;
    int maxSize();
}
