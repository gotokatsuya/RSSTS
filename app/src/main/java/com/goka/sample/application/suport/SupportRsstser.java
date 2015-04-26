package com.goka.sample.application.suport;

import com.goka.rssts.compat.LifecycleCallbacksSupportApplication;
import com.goka.rssts.view.ReportFragment;
import com.goka.sample.application.ActivityLifecycleCallbacksAdapter;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.FragmentActivity;

/**
 * Created by katsuyagoto on 2015/04/26.
 */
public class SupportRsstser {

    private static SupportRsstser sInstance;


    private ActivityLifecycleCallbacksSupportAdapter mActivityLifecycleCallbacksSupportAdapter;

    private ActivityLifecycleCallbacksSupportAdapter.Callback supportCallback = new ActivityLifecycleCallbacksSupportAdapter.Callback() {
        @Override
        public void onCreated(Activity activity) {
            if (activity instanceof FragmentActivity) {
                ReportFragment.apply((FragmentActivity) activity);
            }
        }
    };

    public SupportRsstser(LifecycleCallbacksSupportApplication application) {
        this.mActivityLifecycleCallbacksSupportAdapter = new ActivityLifecycleCallbacksSupportAdapter(application,
                supportCallback);
    }

    public static synchronized void start(LifecycleCallbacksSupportApplication application) {
        if (sInstance == null) {
            sInstance = new SupportRsstser(application);
        }
    }

    public static synchronized void stop(LifecycleCallbacksSupportApplication application) {
        if (sInstance != null) {
            sInstance.mActivityLifecycleCallbacksSupportAdapter.unregister(application);
            sInstance = null;
        }
    }


    private ActivityLifecycleCallbacksAdapter activityLifecycleCallbacksAdapter;

    private ActivityLifecycleCallbacksAdapter.Callback callback = new ActivityLifecycleCallbacksAdapter.Callback() {
        @Override
        public void onCreated(Activity activity) {
            if (activity instanceof FragmentActivity) {
                ReportFragment.apply((FragmentActivity) activity);
            }
        }
    };


    public SupportRsstser(Application application) {
        this.activityLifecycleCallbacksAdapter = new ActivityLifecycleCallbacksAdapter(application, callback);
    }

    public static synchronized void start(Application application) {
        if (sInstance == null) {
            sInstance = new SupportRsstser(application);
        }
    }

    public static synchronized void stop(Application application) {
        if (sInstance != null) {
            sInstance.activityLifecycleCallbacksAdapter.unregister(application);
            sInstance = null;
        }
    }
}
