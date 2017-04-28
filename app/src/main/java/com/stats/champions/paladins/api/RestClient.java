package com.stats.champions.paladins.api;

import com.squareup.okhttp.OkHttpClient;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class RestClient {

    private static RestClient INSTANCE;

    private OkHttpClient mClient;
    private SimpleDateFormat mFormatter;

    public static RestClient getInstance() {
        if (INSTANCE == null)
            INSTANCE = new RestClient();
        return INSTANCE;
    }

    private RestClient() {
        mClient = new OkHttpClient();
        mFormatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.FRANCE);
        mFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public SimpleDateFormat getFormatter() {
        return mFormatter;
    }

    public OkHttpClient getClient() {
        return mClient;
    }
}
