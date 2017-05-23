package com.zksyp.lib_bind;

import android.support.annotation.IdRes;
import android.view.View;

/**
 * Created with Android Studio.
 * User:kaishen
 * Date:2017/5/22
 * Time:下午5:49
 * Desc:
 */

public class Utils {

    public static <T> T findRequiredViewAsType(View source, @IdRes int id, String who,
                                               Class<T> cls) {
        View view = findRequiredView(source, id, who);
        return castView(view, id, who, cls);
    }


    public static <T> T findOptionalViewAsType(View source, @IdRes int id, String who,
                                               Class<T> cls) {
        View view = source.findViewById(id);
        return castView(view, id, who, cls);
    }

    public static View findRequiredView(View source, @IdRes int id, String who) {
        View view = source.findViewById(id);
        if (view != null) {
            return view;
        }
        String name = getResourceEntryName(source, id);
        throw new IllegalStateException("Required view '"
                + name
                + "' with ID "
                + id
                + " for "
                + who
                + " was not found. If this view is optional add '@Nullable' (fields) or '@Optional'"
                + " (methods) annotation.");
    }

    private static String getResourceEntryName(View view, @IdRes int id) {
        if (view.isInEditMode()) {
            return "<unavailable while editing>";
        }
        return view.getContext().getResources().getResourceEntryName(id);
    }

    public static <T> T castView(View view, @IdRes int id, String who, Class<T> cls) {
        try {
            return cls.cast(view);
        } catch (ClassCastException e) {
            String name = getResourceEntryName(view, id);
            throw new IllegalStateException("View '"
                    + name
                    + "' with ID "
                    + id
                    + " for "
                    + who
                    + " was of the wrong type. See cause for more info.", e);
        }
    }

    private Utils() {
        throw new AssertionError("No instances.");
    }
}

