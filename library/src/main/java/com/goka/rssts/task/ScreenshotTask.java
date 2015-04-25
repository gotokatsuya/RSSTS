package com.goka.rssts.task;

import com.goka.rssts.util.IOUtils;
import com.goka.rssts.util.ViewUtils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;

/**
 * Created by katsuyagoto on 2015/04/26.
 */
public class ScreenshotTask extends AsyncTask<Void, Void, File> {

    private Activity activity;

    private Callback callback;

    private Bitmap bitmap;

    private IOException ioException;

    public ScreenshotTask(Activity activity, Callback callback) {
        if (activity == null) {
            throw new IllegalArgumentException("activity must not be null");
        }
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }

        this.activity = activity;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        bitmap = ViewUtils.getDecorViewBitmap(activity);
    }

    @Override
    protected File doInBackground(Void... params) {
        try {
            File bitmapFile = obtainNewBitmapFile();
            IOUtils.saveBitmap(bitmap, bitmapFile);
            return bitmapFile;
        } catch (IOException e) {
            ioException = e;
            return null;
        }
    }

    private File obtainNewBitmapFile() throws IOException {
        File directory = IOUtils.getCacheDirectory(activity);
        String cacheDirectoryPath = directory.getAbsolutePath();
        return IOUtils.newUniqueTempFile(cacheDirectoryPath, "jpg");
    }

    @Override
    protected void onPostExecute(File bitmapFile) {
        if (ioException != null) {
            callback.onCatchIOException(ioException);
        } else {
            callback.onTakeScreenshot(bitmapFile);
        }
    }

    public interface Callback {

        void onTakeScreenshot(File bitmapFile);

        void onCatchIOException(IOException e);

    }

}
