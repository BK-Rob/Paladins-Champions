package com.stats.champions.paladins.api;

import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Observable;
import java.util.TimeZone;

public class RestClient extends Observable {

    private String BASE_URL = "http://api.paladins.com/paladinsapi.svc/";

    private String mResult;

    private OkHttpClient mClient;
    private SimpleDateFormat mFormatter;
    private UserSession mSession;

    public enum REQUEST_TYPE {
        PING(0),
        STATUS(1),
        CONNEXION(2),
        TEST(3),
        PLAYER(4),
        CHAMPIONS(5);

        private int value;

        REQUEST_TYPE(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public RestClient(REQUEST_TYPE type, String name, String... args) {
        mClient = new OkHttpClient();
        mSession = UserSession.getInstance();
        mFormatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.FRANCE);
        mFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        callMethod(type, name, args);
    }

    private void callMethod(REQUEST_TYPE type, String name, String... args) {
        StringBuilder sb = new StringBuilder();
        String time = mFormatter.format(new Date());
        String signature = null;

        if (type == REQUEST_TYPE.CONNEXION || type == REQUEST_TYPE.STATUS)
            signature = generateSignature(name, time, true);
        else
            signature = generateSignature(name, time, false);
        if (type == REQUEST_TYPE.PING)
            sb.append(BASE_URL).append(name).append("JSON");
        else {
            sb.append(BASE_URL).append(name).append("JSON").append("/").append(signature);
            sb.append(time);
            if (args.length > 0)
                sb.append("/");
            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]);
                if (i + 1 < args.length)
                    sb.append("/");
            }
        }
        Log.d("myRequest", sb.toString());
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

        String s = mSession.DEV_ID + methodName + mSession.AUTH_KEY + time;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(s.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            if (isConnexion)
                return mSession.DEV_ID + "/" + hashtext + "/";
            else
                return mSession.DEV_ID + "/" + hashtext + "/" + mSession.getSession() + "/";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getResult() {
        return mResult;
    }

}
