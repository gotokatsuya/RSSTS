package com.goka.rssts.service;


import com.goka.rssts.IIntentReceiveService;
import com.goka.rssts.IIntentReceiveServiceCallback;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
/**
 * Created by katsuyagoto on 2015/04/26.
 */
public class IntentReceiveService extends Service {

    public static Intent createIntent(Context context) {
        return new Intent(context, IntentReceiveService.class);
    }

    public static PendingIntent createPendingIntent(Context context, int requestCode, int flags) {
        Intent intent = createIntent(context);
        return PendingIntent.getService(context, requestCode, intent, flags);
    }

    private RemoteCallbackList<IIntentReceiveServiceCallback> callbackList =
            new RemoteCallbackList<>();

    private IIntentReceiveService.Stub stub = new IIntentReceiveService.Stub() {
        @Override
        public void registerCallback(IIntentReceiveServiceCallback callback)
                throws RemoteException {
            callbackList.register(callback);
        }

        @Override
        public void unregisterCallback(IIntentReceiveServiceCallback callback)
                throws RemoteException {
            callbackList.unregister(callback);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int countOfCallbacks = callbackList.beginBroadcast();
        for (int i = 0; i < countOfCallbacks; i++) {
            try {
                IIntentReceiveServiceCallback callback = callbackList.getBroadcastItem(i);
                callback.onReceiveReportIntent();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        callbackList.finishBroadcast();

        stopSelf();
        return START_NOT_STICKY;
    }

}
