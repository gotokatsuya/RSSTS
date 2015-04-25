package com.goka.rssts.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by katsuyagoto on 2015/04/26.
 */
public class AppUtils {

    public static CharSequence getApplicationName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return context.getApplicationInfo().loadLabel(packageManager);
    }

    public static String getApplicationVersion(String packageName, Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_META_DATA);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
