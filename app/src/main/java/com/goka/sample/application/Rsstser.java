package com.goka.sample.application;

import com.goka.rssts.view.ReportFragment;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.FragmentActivity;

/**
 * Created by katsuyagoto on 2015/04/26.
 */
public class Rsstser {

    private static Rsstser sInstance;

    private ActivityLifecycleCallbacksAdapter activityLifecycleCallbacksAdapter;

    private ActivityLifecycleCallbacksAdapter.Callback callback = new ActivityLifecycleCallbacksAdapter.Callback() {
        @Override
        public void onCreated(Activity activity) {
            if (activity instanceof FragmentActivity) {
                ReportFragment.apply((FragmentActivity) activity);
            }
        }
    };


    public Rsstser(Application application) {
        this.activityLifecycleCallbacksAdapter = new ActivityLifecycleCallbacksAdapter(application, callback);
    }

    public static synchronized void start(Application application) {
        if (sInstance == null) {
            sInstance = new Rsstser(application);
        }
    }

    public static synchronized void stop(Application application) {
        if (sInstance != null) {
            sInstance.activityLifecycleCallbacksAdapter.unregister(application);
            sInstance = null;
        }
    }
}
