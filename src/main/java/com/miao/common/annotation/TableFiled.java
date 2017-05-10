package com.miao.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by pp on 2017/5/10.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableFiled {
    boolean isTableFiled() default true;
    boolean isString() default false;
    boolean isChar() default false;
}
