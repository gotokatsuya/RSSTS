package com.goka.rssts.compat;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by katsuyagoto on 2015/04/26.
 */
public class LifecycleCallbacksSupportApplication extends Application {

    public static interface ActivityLifecycleCallbacksCompat {
        void onActivityCreated(Activity activity,
                Bundle savedInstanceState);

        void onActivityStarted(Activity activity);

        void onActivityResumed(Activity activity);

        void onActivityPaused(Activity activity);

        void onActivityStopped(Activity activity);

        void onActivitySaveInstanceState(Activity activity,
                Bundle outState);

        void onActivityDestroyed(Activity activity);
    }

    static interface ActivityLifecycleCallbacksRegistry {

        void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksCompat callback);

        void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacksCompat callback);

        void dispatchActivityCreated(Activity activity,
                Bundle savedInstanceState);

        void dispatchActivityStarted(Activity activity);

        void dispatchActivityResumed(Activity activity);

        void dispatchActivityPaused(Activity activity);

        void dispatchActivityStopped(Activity activity);

        void dispatchActivitySaveInstanceState(Activity activity,
                Bundle outState);

        void dispatchActivityDestroyed(Activity activity);

    }

    /**
     * {@link ActivityLifecycleCallbacksRegistry}のICS以前の実装
     */
    private static final class LegacyActivityLifecycleCallbacksRegistry implements ActivityLifecycleCallbacksRegistry {

        private WeakReference<Application> mApplication;
        private List<ActivityLifecycleCallbacksCompat> mActivityLifecycleCallbacks;

        public LegacyActivityLifecycleCallbacksRegistry(Application application) {
            mApplication = new WeakReference<Application>(application);
            mActivityLifecycleCallbacks = new ArrayList<LifecycleCallbacksSupportApplication.ActivityLifecycleCallbacksCompat>();
        }

        @Override
        public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksCompat callback) {
            final Application application = mApplication.get();
            if (application == null) {
                return;
            }
            synchronized (mActivityLifecycleCallbacks) {
                mActivityLifecycleCallbacks.add(callback);
            }
        }

        @Override
        public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacksCompat callback) {
            final Application application = mApplication.get();
            if (application == null) {
                return;
            }
            synchronized (mActivityLifecycleCallbacks) {
                mActivityLifecycleCallbacks.remove(callback);
            }
        }

        @Override
        public void dispatchActivityCreated(Activity activity,
                Bundle savedInstanceState) {
            Object[] callbacks = collectActivityLifecycleCallbacks();
            if (callbacks != null) {
                for (int i = 0; i < callbacks.length; i++) {
                    ((ActivityLifecycleCallbacksCompat) callbacks[i]).onActivityCreated(activity, savedInstanceState);
                }
            }
        }

        @Override
        public void dispatchActivityStarted(Activity activity) {
            Object[] callbacks = collectActivityLifecycleCallbacks();
            if (callbacks != null) {
                for (int i = 0; i < callbacks.length; i++) {
                    ((ActivityLifecycleCallbacksCompat) callbacks[i]).onActivityStarted(activity);
                }
            }
        }

        @Override
        public void dispatchActivityResumed(Activity activity) {
            Object[] callbacks = collectActivityLifecycleCallbacks();
            if (callbacks != null) {
                for (int i = 0; i < callbacks.length; i++) {
                    ((ActivityLifecycleCallbacksCompat) callbacks[i]).onActivityResumed(activity);
                }
            }
        }

        @Override
        public void dispatchActivityPaused(Activity activity) {
            Object[] callbacks = collectActivityLifecycleCallbacks();
            if (callbacks != null) {
                for (int i = 0; i < callbacks.length; i++) {
                    ((ActivityLifecycleCallbacksCompat) callbacks[i]).onActivityPaused(activity);
                }
            }
        }

        @Override
        public void dispatchActivityStopped(Activity activity) {
            Object[] callbacks = collectActivityLifecycleCallbacks();
            if (callbacks != null) {
                for (int i = 0; i < callbacks.length; i++) {
                    ((ActivityLifecycleCallbacksCompat) callbacks[i]).onActivityStopped(activity);
                }
            }
        }

        @Override
        public void dispatchActivitySaveInstanceState(Activity activity,
                Bundle outState) {
            Object[] callbacks = collectActivityLifecycleCallbacks();
            if (callbacks != null) {
                for (int i = 0; i < callbacks.length; i++) {
                    ((ActivityLifecycleCallbacksCompat) callbacks[i]).onActivitySaveInstanceState(activity, outState);
                }
            }
        }

        @Override
        public void dispatchActivityDestroyed(Activity activity) {
            Object[] callbacks = collectActivityLifecycleCallbacks();
            if (callbacks != null) {
                for (int i = 0; i < callbacks.length; i++) {
                    ((ActivityLifecycleCallbacksCompat) callbacks[i]).onActivityDestroyed(activity);
                }
            }
        }

        private Object[] collectActivityLifecycleCallbacks() {
            Object[] callbacks = null;
            synchronized (mActivityLifecycleCallbacks) {
                if (mActivityLifecycleCallbacks.size() > 0) {
                    callbacks = mActivityLifecycleCallbacks.toArray();
                }
            }
            return callbacks;
        }
    }

    /**
     * {@link ActivityLifecycleCallbacksRegistry}のICS以降の実装
     */
    @TargetApi(14)
    private static final class IceCreamSandwichActivityLifecycleCallbacksRegistry implements ActivityLifecycleCallbacksRegistry {

        private static final class DelegatingActivityLifecycleCallbacks implements android.app.Application.ActivityLifecycleCallbacks {

            private ActivityLifecycleCallbacksCompat mCompat;

            public DelegatingActivityLifecycleCallbacks(ActivityLifecycleCallbacksCompat compat) {
                mCompat = compat;
            }

            @Override
            public void onActivityCreated(Activity activity,
                    Bundle savedInstanceState) {
                mCompat.onActivityCreated(activity, savedInstanceState);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                mCompat.onActivityStarted(activity);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                mCompat.onActivityResumed(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                mCompat.onActivityPaused(activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                mCompat.onActivityStopped(activity);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity,
                    Bundle outState) {
                mCompat.onActivitySaveInstanceState(activity, outState);
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                mCompat.onActivityDestroyed(activity);
            }
        }

        private WeakReference<Application> mApplication;
        private Map<ActivityLifecycleCallbacksCompat, android.app.Application.ActivityLifecycleCallbacks> mDelegates;

        public IceCreamSandwichActivityLifecycleCallbacksRegistry(Application application) {
            mApplication = new WeakReference<Application>(application);
            mDelegates = new HashMap<LifecycleCallbacksSupportApplication.ActivityLifecycleCallbacksCompat, Application.ActivityLifecycleCallbacks>();
        }

        @Override
        public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksCompat callback) {
            final Application application = mApplication.get();
            if (application == null) {
                return;
            }
            final android.app.Application.ActivityLifecycleCallbacks delegate = delegateOf(callback);
            application.registerActivityLifecycleCallbacks(delegate);
        }

        @Override
        public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacksCompat callback) {
            final Application application = mApplication.get();
            if (application == null) {
                return;
            }
            final android.app.Application.ActivityLifecycleCallbacks delegate = delegateOf(callback);
            application.unregisterActivityLifecycleCallbacks(delegate);
        }

        private android.app.Application.ActivityLifecycleCallbacks delegateOf(ActivityLifecycleCallbacksCompat compat) {
            synchronized (mDelegates) {
                if (mDelegates.containsKey(compat)) {
                    return mDelegates.get(compat);
                }

                android.app.Application.ActivityLifecycleCallbacks delegate = new DelegatingActivityLifecycleCallbacks(compat);
                mDelegates.put(compat, delegate);
                return delegate;
            }
        }

        @Override
        public void dispatchActivityCreated(Activity activity,
                Bundle savedInstanceState) {
            // ICS以降のApplicationのオリジナルのメソッドが実行されるので何もしない
        }

        @Override
        public void dispatchActivityStarted(Activity activity) {
            // ICS以降のApplicationのオリジナルのメソッドが実行されるので何もしない
        }

        @Override
        public void dispatchActivityResumed(Activity activity) {
            // ICS以降のApplicationのオリジナルのメソッドが実行されるので何もしない
        }

        @Override
        public void dispatchActivityPaused(Activity activity) {
            // ICS以降のApplicationのオリジナルのメソッドが実行されるので何もしない
        }

        @Override
        public void dispatchActivityStopped(Activity activity) {
            // ICS以降のApplicationのオリジナルのメソッドが実行されるので何もしない
        }

        @Override
        public void dispatchActivitySaveInstanceState(Activity activity,
                Bundle outState) {
            // ICS以降のApplicationのオリジナルのメソッドが実行されるので何もしない
        }

        @Override
        public void dispatchActivityDestroyed(Activity activity) {
            // ICS以降のApplicationのオリジナルのメソッドが実行されるので何もしない
        }
    }

    private ActivityLifecycleCallbacksRegistry mActivityLifecycleCallbacksRegistry;

    public LifecycleCallbacksSupportApplication() {
        setupActivityLifecycleCallbacksRegistry();
    }

    private void setupActivityLifecycleCallbacksRegistry() {
        if (introducedActivityLifecycleCallbacks()) {
            setupDelegatingActivityLifecycleCallbacks();
        } else {
            setupCompatActivityLifecycleCallbacks();
        }
    }

    private boolean introducedActivityLifecycleCallbacks() {
        try {
            Class.forName("android.app.Application$ActivityLifecycleCallbacks");
            return true;
        } catch (ClassNotFoundException e) {
        }
        return false;
    }

    private void setupCompatActivityLifecycleCallbacks() {
        mActivityLifecycleCallbacksRegistry = new LegacyActivityLifecycleCallbacksRegistry(this);
    }

    private void setupDelegatingActivityLifecycleCallbacks() {
        mActivityLifecycleCallbacksRegistry = new IceCreamSandwichActivityLifecycleCallbacksRegistry(this);
    }

    public void registerSupportActivityLifecycleCallbacks(ActivityLifecycleCallbacksCompat callback) {
        mActivityLifecycleCallbacksRegistry.registerActivityLifecycleCallbacks(callback);
    }

    public void unregisterSupportActivityLifecycleCallbacksCompat(ActivityLifecycleCallbacksCompat callback) {
        mActivityLifecycleCallbacksRegistry.unregisterActivityLifecycleCallbacks(callback);
    }

    void dispatchActivityCreatedCompat(Activity activity,
            Bundle savedInstanceState) {
        mActivityLifecycleCallbacksRegistry.dispatchActivityCreated(activity, savedInstanceState);
    }

    void dispatchActivityStartedCompat(Activity activity) {
        mActivityLifecycleCallbacksRegistry.dispatchActivityStarted(activity);
    }

    void dispatchActivityResumedCompat(Activity activity) {
        mActivityLifecycleCallbacksRegistry.dispatchActivityResumed(activity);
    }

    void dispatchActivityPausedCompat(Activity activity) {
        mActivityLifecycleCallbacksRegistry.dispatchActivityPaused(activity);
    }

    void dispatchActivityStoppedCompat(Activity activity) {
        mActivityLifecycleCallbacksRegistry.dispatchActivityStopped(activity);
    }

    void dispatchActivitySaveInstanceStateCompat(Activity activity,
            Bundle outState) {
        mActivityLifecycleCallbacksRegistry.dispatchActivitySaveInstanceState(activity, outState);
    }

    void dispatchActivityDestroyedCompat(Activity activity) {
        mActivityLifecycleCallbacksRegistry.dispatchActivityDestroyed(activity);
    }
}
