package com.goka.sample.application.suport;

import com.goka.rssts.Rssts;
import com.goka.rssts.compat.LifecycleCallbacksSupportApplication;
import com.goka.sample.AppConfig;

/**
 * Created by katsuyagoto on 2015/04/26.
 */
public class SampleSupportApplication extends LifecycleCallbacksSupportApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        if (AppConfig.DEBUG) {

            // Initialize Rssts of library
            Rssts.initialize("your slack token", "your slack channel ID");

            // Start Rsstser of Your Application
            SupportRsstser.start(this);
        }

    }

    // Maybe, this method is called when you use emulator
    @Override
    public void onTerminate() {

        if (AppConfig.DEBUG) {
            // Stop Rsstser of Your Application
            SupportRsstser.stop(this);
        }

        super.onTerminate();
    }
}
