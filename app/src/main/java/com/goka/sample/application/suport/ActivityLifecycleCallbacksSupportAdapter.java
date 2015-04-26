package com.goka.sample.application.suport;

import com.goka.rssts.compat.LifecycleCallbacksSupportApplication;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by katsuyagoto on 2015/04/26.
 */
public class ActivityLifecycleCallbacksSupportAdapter implements LifecycleCallbacksSupportApplication.ActivityLifecycleCallbacksCompat {

    public static interface Callback {
        public void onCreated(Activity activity);
    }

    private Callback callback;

    public ActivityLifecycleCallbacksSupportAdapter(LifecycleCallbacksSupportApplication application, Callback callback) {
        application.registerSupportActivityLifecycleCallbacks(this);
        this.callback = callback;
    }

    public void unregister(LifecycleCallbacksSupportApplication application) {
        application.unregisterSupportActivityLifecycleCallbacksCompat(this);
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        callback.onCreated(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }
}