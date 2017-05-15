package com.zksyp.lib_bind;


import android.app.Activity;
import android.view.View;

/**
 * Created with Android Studio.
 * User:kaishen
 * Date:2017/5/15
 * Time:下午2:17
 * Desc:
 */

public enum Finder {

    VIEW {
        @Override
        public View findViewById(Object source, int id) {
            return ((View) source).findViewById(id);
        }
    },
    ACTIVITY {
        @Override
        public View findViewById(Object source, int id) {
            return ((Activity) source).findViewById(id);
        }

    };

    public <T> T castView(View view, int id, String who) {
        return (T) view;
    }

    public abstract View findViewById(Object source, int id);
}


