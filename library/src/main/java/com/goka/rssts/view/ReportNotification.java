package com.goka.rssts.view;

import com.goka.rssts.Config;
import com.goka.rssts.R;
import com.goka.rssts.service.IntentReceiveService;
import com.goka.rssts.util.AppUtils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

/**
 * Created by katsuyagoto on 2015/04/26.
 */
public class ReportNotification {

    private static final String TAG = ReportNotification.class.getName();

    private static final int NOTIFICATION_ID = 0;

    public static void show(Context context) {
        PendingIntent pendingIntent = IntentReceiveService.createPendingIntent(context, 0, 0);
        Notification notification = buildNotification(context, pendingIntent);

        NotificationManager notificationManager = getNotificationManager(context);
        notificationManager.notify(TAG, NOTIFICATION_ID, notification);
    }

    public static void cancel(Context context) {
        NotificationManager notificationManager = getNotificationManager(context);
        notificationManager.cancel(TAG, NOTIFICATION_ID);
    }

    private static Notification buildNotification(Context context, PendingIntent pendingIntent) {
        String applicationName = AppUtils.getApplicationName(context).toString();
        String title = context.getResources().getString(R.string.notification_title);
        String description = context.getResources().getString(R.string.notification_description, applicationName,
                Config.CHANNELS);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker(title);
        builder.setContentTitle(title);
        builder.setContentText(description);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentIntent(pendingIntent);
        return builder.build();
    }

    protected static NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
