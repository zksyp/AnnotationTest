package com.zksyp.annotationtest.AnnotateHelper;

import android.app.Activity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with Android Studio.
 * User:kaishen
 * Date:2017/5/10
 * Time:下午3:49
 * Desc:
 */

public class AnnotateBindUtils {

    public static void bind(Activity activity) {
        Class activityClass = activity.getClass();
        Field[] fields = activityClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(BindView1.class)) {
                BindView1 bindView1 = field.getAnnotation(BindView1.class);
                if (bindView1 != null) {
                    try {
                        Method method = activityClass.getMethod("findViewById", int.class);
                        method.setAccessible(true);
                        Object resultView = method.invoke(activity, bindView1.value());
                        field.setAccessible(true);
                        field.set(activity, resultView);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            } else if (field.isAnnotationPresent(BindView2.class)) {
                BindView2 bindView2 = field.getAnnotation(BindView2.class);
                if (bindView2 != null) {
                    try {
                        field.setAccessible(true);//可以获取该类中置为private的类的值
                        field.set(activity, activity.findViewById((bindView2.value())));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
