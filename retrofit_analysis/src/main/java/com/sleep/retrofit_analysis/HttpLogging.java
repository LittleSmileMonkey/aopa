package com.sleep.retrofit_analysis;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * author：xingkong on 2019-06-02
 * e-mail：xingkong@changjinglu.net
 */
public class HttpLogging {
    private static final String TAG = HttpLogging.class.getSimpleName();

    static final HttpLoggingInterceptor httpLoggingInterceptor;

    static {
        httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    private static class HttpLogger implements HttpLoggingInterceptor.Logger {

        @Override
        public void log(String message) {
            Log.i(TAG, message);
        }

    }
}
