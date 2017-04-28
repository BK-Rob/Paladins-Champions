package com.stats.champions.paladins;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.stats.champions.paladins.api.RestClient;
import com.stats.champions.paladins.api.RestClient.REQUEST_TYPE;

import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreen extends Activity implements Observer{

    @BindView(R.id.status)
    TextView mStatus;

    @BindView(R.id.patch)
    TextView mPatch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        ButterKnife.bind(this);

        new RestClient(REQUEST_TYPE.PING, "ping").addObserver(this);
        new RestClient(REQUEST_TYPE.STATUS, "gethirezserverstatus").addObserver(this);
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 4000);*/
    }

    private void parseLine(String res) {
        String regex = "(\\w+\\s+\\(+.+\\))+\\s+(\\[.*])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(res);

        String s = null;
        while (matcher.find()) {
            s = matcher.group(1) + " " + matcher.group(2);
        }
        if (s == null)
            mPatch.setText("API is down or under maintenance.");
        else
            mPatch.setText(s);
    }

    @Override
    public void update(final Observable o, final Object arg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RestClient client = (RestClient) o;

                switch ((REQUEST_TYPE) arg) {
                    case PING:
                        parseLine(client.getResult());
                        break;
                    case STATUS:
                        Log.d("result", client.getResult());
                        break;
                }
            }
        });
    }
}
