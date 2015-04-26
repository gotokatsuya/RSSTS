package com.goka.rssts.util;

import com.goka.rssts.Config;

import android.util.Log;

/**
 * Created by katsuyagoto on 2015/04/26.
 */
public class LogUtils {

    public static void V(String tag, String msg) {
        if (Config.ENABLE_LOG) {
            Log.v(tag, msg);
        }
    }

    public static void D(String tag, String msg) {
        if (Config.ENABLE_LOG) {
            Log.d(tag, msg);
        }
    }
}
