package com.stats.champions.paladins.api;

import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Observable;
import java.util.TimeZone;

import static com.stats.champions.paladins.api.Constants.*;

public class RestClient extends Observable {

    private String mResult;

    private static OkHttpClient mClient;
    private static SimpleDateFormat mFormatter;

    public enum REQUEST_TYPE {
        CONNEXION,
        TEST,
        PLAYER
    }

    public RestClient(REQUEST_TYPE type, String name, String... args) {
        mClient = new OkHttpClient();
        mFormatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.FRANCE);
        mFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        callMethod(type, name, args);
    }

    private void callMethod(REQUEST_TYPE type, String name, String... args) {
        StringBuilder sb = new StringBuilder();
        String time = mFormatter.format(new Date());
        String signature = null;

        if (type == REQUEST_TYPE.CONNEXION)
            signature = generateSignature(name, time, true);
        else
            signature = generateSignature(name, time, false);
        sb.append(BASE_URL).append(name).append("JSON").append("/").append(signature);
        sb.append(time);
        if (args.length > 0)
            sb.append("/");
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
            if (i + 1 < args.length)
                sb.append("/");
        }
        Log.d("myRequest", sb.toString());
        Log.d("myTime", time);
        Request req = new Request.Builder()
                .url(sb.toString()).build();
        mClient.newCall(req).enqueue(getCallback(type));
    }

    private Callback getCallback(final REQUEST_TYPE type) {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mResult = "{\"ret_msg\":\"FAILURE\",\"message\":\"Something went wrong.\"}";
                setChanged();
                notifyObservers(type);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                mResult = response.body().string();
                setChanged();
                notifyObservers(type);
            }
        };
    }

    private String generateSignature(String methodName, String time, boolean isConnexion) {

        String s = DEV_ID + methodName + AUTH_KEY + time;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest)
                hexString.append(Integer.toHexString(0xFF & aMessageDigest));

            if (isConnexion)
                return DEV_ID + "/" + hexString.toString() + "/";
            else
                return DEV_ID + "/" + hexString.toString() + "/" + mSession + "/";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getResult() {
        return mResult;
    }

}
