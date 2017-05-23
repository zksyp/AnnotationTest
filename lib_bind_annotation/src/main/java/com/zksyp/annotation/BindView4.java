package com.zksyp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with Android Studio.
 * User:kaishen
 * Date:2017/5/23
 * Time:上午10:02
 * Desc:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface BindView4 {
    int value();
}
