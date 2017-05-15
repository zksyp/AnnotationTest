package com.zksyp.lib_bind;

/**
 * Created with Android Studio.
 * User:kaishen
 * Date:2017/5/15
 * Time:下午2:27
 * Desc:
 */

public interface AbstractBinder<T> {

    void bind(Finder finder, T target, Object resource);
}
