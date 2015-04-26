package com.goka.sample.application;

import com.goka.rssts.Rssts;
import com.goka.sample.AppConfig;

import android.app.Application;

/**
 * Created by katsuyagoto on 2015/04/26.
 */
public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (AppConfig.DEBUG) {

            // Initialize Rssts of library
            Rssts.initialize("your slack token", "your slack channel ID");

            // Start Rsstser of Your Application
            Rsstser.start(this);
        }

    }

    // Maybe, this method is called when you use emulator
    @Override
    public void onTerminate() {

        if (AppConfig.DEBUG) {
            // Stop Rsstser of Your Application
            Rsstser.stop(this);
        }

        super.onTerminate();
    }
}
