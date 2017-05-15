package com.zksyp.annotationtest.AnnotateHelper;

import android.app.Activity;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created with Android Studio.
 * User:kaishen
 * Date:2017/5/10
 * Time:下午5:30
 * Desc:
 */

public class TestEvent {
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showEvent(Activity activity) {
        Toast.makeText(activity, "test event toast", Toast.LENGTH_SHORT).show();
    }
}
