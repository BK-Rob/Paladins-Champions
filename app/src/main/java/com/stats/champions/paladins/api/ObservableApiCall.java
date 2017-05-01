package com.stats.champions.paladins.api;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class ObservableApiCall extends Observable implements Observer {

    private String BASE_URL = "http://api.paladins.com/paladinsapi.svc/";
    private String mResult;

    private String mName;
    private String[] mArgs;

    private Activity mContext;
    private RestClient mClient;
    private UserSession mSession;

    public ObservableApiCall(Activity context, String name, String... args) {
        mContext = context;
        mSession = UserSession.getInstance();
        mClient = RestClient.getInstance();
        mName = name;
        mArgs = args;

        if ((!Endpoint.CreateSession.equals(name) && !Endpoint.Ping.equals(name))
                && mSession.getSession() == null)
            new ObservableApiCall(mContext, Endpoint.CreateSession);
        else
            callMethod(name, args);
    }

    private void callMethod(String name, String... args) {
        StringBuilder sb = new StringBuilder();
        String time = mClient.getFormatter().format(new Date());
        String signature = null;

        if (name.equals(Endpoint.CreateSession) || name.equals(Endpoint.GetHirezServerStatus))
            signature = generateSignature(name, time, true);
        else
            signature = generateSignature(name, time, false);

        if (name.equals(Endpoint.Ping))
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
        mClient.getClient().newCall(req).enqueue(getCallback(name));
    }

    private Callback getCallback(final String type) {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mResult = "{\"ret_msg\":\"FAILURE\",\"message\":\"Something went wrong.\"}";
                Log.d("myObservableApiCall", "onFailure");
                notifyObserver(type);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                mResult = response.body().string();
                Log.d("myObservableApiCall", "onResponse: " + mResult);
                notifyObserver(type);
            }
        };
    }

    private void notifyObserver(final String type) {
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setChanged();
                notifyObservers(type);
            }
        });
    }

    private String generateSignature(String methodName, String time, boolean isConnexion) {

        String s = mSession.DEV_ID + methodName + mSession.AUTH_KEY + time;
        String session = mSession.getSession();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(s.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            if (isConnexion)
                return mSession.DEV_ID + "/" + hashtext + "/";
            else
                return mSession.DEV_ID + "/" + hashtext + "/" + session + "/";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getResult() {
        return mResult;
    }

    @Override
    public void update(Observable o, Object arg) {
        final ObservableApiCall client = (ObservableApiCall) o;
        Log.d("myType", arg.toString());

        switch ((String) arg) {
            case Endpoint.CreateSession:
                callMethod(mName, mArgs);
                break;
        }
    }
}
