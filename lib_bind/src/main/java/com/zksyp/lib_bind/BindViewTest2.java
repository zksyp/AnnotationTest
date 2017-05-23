package com.zksyp.lib_bind;

import android.app.Activity;
import android.view.View;

import com.zksyp.viewInject.ViewBindInjector;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with Android Studio.
 * User:kaishen
 * Date:2017/5/22
 * Time:下午7:52
 * Desc:
 */

public class BindViewTest2 {

    static final Map<Class<?>, Constructor<?>> BINDERS = new LinkedHashMap<>();

    public static void bind(Activity target) {
        Constructor<?> bindCtr = findBinderCtr(target);
    }

    private static Constructor<?> findBinderCtr(Activity target) {
        Class<?> clazz = target.getClass();
        Constructor<?> bindCtr = BINDERS.get(clazz);
        if (bindCtr == null) {
            try {
                Class binderClazz = Class.forName(clazz.getName() + ViewBindInjector.SUFFIX);
                bindCtr = (Constructor<?>) binderClazz.getConstructor(clazz, View.class);
                BINDERS.put(clazz, bindCtr);
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return bindCtr;
    }
}
