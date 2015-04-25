package com.goka.rssts.helper;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by katsuyagoto on 2015/04/26.
 */
public class ToastHelper {

    public static void showShort(Activity activity, CharSequence text) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(Activity activity, int resId) {
        Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Activity activity, CharSequence text) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
    }

    public static void showLong(Activity activity, int resId) {
        Toast.makeText(activity, resId, Toast.LENGTH_LONG).show();
    }
}
