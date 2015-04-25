package com.goka.rssts.model;

import com.goka.rssts.util.AppUtils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.Locale;

/**
 * Created by katsuyagoto on 2015/04/26.
 */
public class DeviceProfile {

    private String packageName;
    private String version;
    private String manufacturer;
    private String model;
    private String sdkInt;
    private String language;
    private String country;
    private int screenLayout;
    private float density;

    public DeviceProfile(Context context) {
        Resources resources = context.getResources();
        this.packageName = context.getPackageName();
        this.version = AppUtils.getApplicationVersion(packageName, context);
        this.manufacturer = android.os.Build.MANUFACTURER;
        this.model = android.os.Build.MODEL;
        this.sdkInt = String.valueOf(android.os.Build.VERSION.SDK_INT);

        Locale locale = resources.getConfiguration().locale;
        this.language = locale.getLanguage();
        this.country = locale.getCountry();

        this.screenLayout = Configuration.SCREENLAYOUT_SIZE_MASK & resources.getConfiguration().screenLayout;
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        this.density = metrics.density;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("packageName=").append(packageName).append("\n");
        builder.append("app_version=").append(version).append("\n");
        builder.append("manufacturer=").append(manufacturer).append("\n");
        builder.append("model=").append(model).append("\n");
        builder.append("sdk-int=").append(sdkInt).append("\n");
        builder.append("language=").append(language).append("\n");
        builder.append("country=").append(country).append("\n");
        builder.append("screen-layout=").append(screenLayout).append("\n");
        builder.append("density=").append(density).append("\n");
        return builder.toString();
    }

    public String description() {
        StringBuilder builder = new StringBuilder();
        builder.append("packageName=").append(packageName).append(",").append("\n");
        builder.append("app_version=").append(version).append("\n");
        return builder.toString();
    }
}
