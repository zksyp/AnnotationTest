package com.zksyp.annotationtest.AnnotateHelper;

import android.support.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with Android Studio.
 * User:kaishen
 * Date:2017/5/10
 * Time:下午4:44
 * Desc:
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BindView2 {

    @IdRes
    int value();
}
