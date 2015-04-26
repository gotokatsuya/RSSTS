package com.goka.rssts.request;

import com.goka.rssts.Config;
import com.goka.rssts.util.LogUtils;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by katsuyagoto on 2015/04/26.
 */
public class SlackClient {

    public static final String BASE_API = "https://slack.com/api";

    public static final String SLACK_FILE_UPLOAD = "/files.upload";

    public interface Slack {
        @Multipart
        @POST(SLACK_FILE_UPLOAD)
        void sendScreenShot(
                @Part("token") String token,
                @Part("title") String title,
                @Part("file") TypedFile screenShotFile,
                @Part("channels") String channels,
                Callback<JSONObject> callback
        );
    }

    private static Slack sSlack;

    public static void initialize() {

        RestAdapter.Log restLogs = new RestAdapter.Log() {
            @Override
            public void log(String s) {
                LogUtils.D("CLIENT", s);
            }
        };

        // create client
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(1, TimeUnit.MINUTES);
        okHttpClient.setWriteTimeout(1, TimeUnit.MINUTES);
        okHttpClient.setConnectTimeout(1, TimeUnit.MINUTES);

        //slack
        RestAdapter slackRestAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_API)
                .setLog(restLogs)
                .setClient(new OkClient(okHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        sSlack = slackRestAdapter.create(Slack.class);

    }

    /**
     * Upload Screenshot to slack channel
     *
     */
    public static void uploadScreenShot(String title, File screenshotFile, Callback<JSONObject> callback) {
        TypedFile typedScreenshotFile = new TypedFile("multipart/form-data", screenshotFile);
        sSlack.sendScreenShot(Config.SLACK_TOKEN, title, typedScreenshotFile, Config.SLACK_CHANNELS, callback);
    }
}
