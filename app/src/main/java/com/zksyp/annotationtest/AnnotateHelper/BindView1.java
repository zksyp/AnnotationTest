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
 * Time:下午3:46
 * Desc:
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindView1 {

    @IdRes
    int value();
}
