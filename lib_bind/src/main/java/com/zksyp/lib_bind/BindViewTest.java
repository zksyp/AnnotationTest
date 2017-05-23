package com.zksyp.lib_bind;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.zksyp.viewInject.ViewBindInjector;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with Android Studio.
 * User:kaishen
 * Date:2017/5/15
 * Time:下午2:32
 * Desc:
 */

public class BindViewTest {
    private static final Map<Class<?>, AbstractBinder<Object>> BINDERS = new LinkedHashMap<>();

    public static void bind(Activity activity) {
        AbstractBinder<Object> binder = findBinder(activity);
        binder.bind(Finder.ACTIVITY, activity, activity);
    }

    public static void bind(View view) {
    }

    public static void bind(Object target, View view) {
        AbstractBinder<Object> binder = findBinder(target);
        binder.bind(Finder.VIEW, target, view);
    }

    private static AbstractBinder<Object> findBinder(Object activity) {
        Class<?> clazz = activity.getClass();
        AbstractBinder<Object> binder = BINDERS.get(clazz);
        Log.e("className", clazz.getSimpleName());
        if (binder == null) {
            try {
                Class binderClazz = Class.forName(clazz.getName() + ViewBindInjector.SUFFIX);
                binder = (AbstractBinder<Object>) binderClazz.newInstance();
                if (binder != null) {
                    Log.e("binder", binder.toString());
                }
                BINDERS.put(clazz, binder);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return binder;
    }


}
