package com.goka.rssts.view;

import com.goka.rssts.IIntentReceiveService;
import com.goka.rssts.IIntentReceiveServiceCallback;
import com.goka.rssts.R;
import com.goka.rssts.model.DeviceProfile;
import com.goka.rssts.request.SlackClient;
import com.goka.rssts.service.IntentReceiveService;
import com.goka.rssts.task.ScreenshotTask;
import com.goka.rssts.helper.ToastHelper;
import com.goka.rssts.util.LogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.io.File;
import java.io.IOException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by katsuyagoto on 2015/04/26.
 */
public class ReportFragment extends BaseFragment {

    private static final String TAG = ReportFragment.class.getName();

    public static ReportFragment newInstance() {
        ReportFragment fragment = new ReportFragment();
        return fragment;
    }

    public static ReportFragment apply(FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        ReportFragment fragment =
                (ReportFragment) fragmentManager.findFragmentByTag(TAG);

        if (fragment != null) {
            return fragment;
        }

        fragment = newInstance();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment, ReportFragment.class.getName());
        fragmentTransaction.commit();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        ReportNotification.show(getActivity());

        Intent intent = IntentReceiveService.createIntent(getActivity());
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onPause() {
        super.onPause();

        ReportNotification.cancel(getActivity());

        unbindService(connection);
    }

    private IIntentReceiveService intentReceiverService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            intentReceiverService = IIntentReceiveService.Stub.asInterface(service);
            try {
                intentReceiverService.registerCallback(callback);
            } catch (RemoteException e) {
                intentReceiverService = null;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            intentReceiverService = null;
        }
    };

    private IIntentReceiveServiceCallback callback = new IIntentReceiveServiceCallback.Stub() {
        @Override
        public void onReceiveReportIntent() throws RemoteException {
            try {
                takeScreenshotThenUploadToSlack();
            } catch (IOException e) {
                ToastHelper.showShort(getActivity(), R.string.failed_to_take_screen_shot);
            }
        }
    };

    private void takeScreenshotThenUploadToSlack() throws IOException {
        ProgressDialogFragment.show(getActivity(), R.string.just_a_moment);
        new ScreenshotTask(getActivity(), new ScreenshotTask.Callback() {
            @Override
            public void onTakeScreenshot(File bitmapFile) {
                final FragmentActivity activity = getActivity();
                if (activity == null) {
                    return;
                }

                // Get device information
                DeviceProfile deviceProfile = new DeviceProfile(activity);

                // Upload file to slack
                SlackClient.uploadScreenShot(deviceProfile.description(), bitmapFile, new Callback<JSONObject>() {
                    @Override
                    public void success(JSONObject jsonObject, Response response) {
                        ProgressDialogFragment.dismiss(activity);
                        ToastHelper.showShort(activity, R.string.successful_reporting_to_slack);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        LogUtils.D(TAG, error.getLocalizedMessage());
                        ProgressDialogFragment.dismiss(activity);
                        ToastHelper.showShort(activity, R.string.failed_to_report_to_slack);
                    }
                });

            }

            @Override
            public void onCatchIOException(IOException e) {
                ProgressDialogFragment.dismiss(getActivity());
                ToastHelper.showShort(getActivity(), R.string.failed_to_take_screen_shot);
            }
        }).execute();
    }
}
