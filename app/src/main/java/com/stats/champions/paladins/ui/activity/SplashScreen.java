package com.stats.champions.paladins.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.stats.champions.paladins.R;
import com.stats.champions.paladins.api.ApiParser;
import com.stats.champions.paladins.api.Endpoint;
import com.stats.champions.paladins.api.ObservableApiCall;
import com.stats.champions.paladins.api.PaladinSession;
import com.stats.champions.paladins.firebase.FirebaseAnalyticsEvents;
import com.stats.champions.paladins.model.Champion;
import com.stats.champions.paladins.model.Item;

import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreen extends Activity implements Observer, ApiParser.OnDataStored {

    @BindView(R.id.status)
    TextView mStatus;

    @BindView(R.id.patch)
    TextView mPatch;

    @BindView(R.id.spin_kit)
    SpinKitView mSpinKit;

    private Handler mHandler;
    private Runnable mActivityRunnable;
    private SharedPreferences mShared;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        ButterKnife.bind(this);
        FirebaseAnalyticsEvents.sendActivityEvent(this, this.getTitle().toString(), "STARTUP_APPLICATION");

        mShared = getSharedPreferences(getString(R.string.shared_pref_file), MODE_PRIVATE);

        mSpinKit.setIndeterminateDrawable(new FoldingCube());
        mHandler = new Handler();
        mActivityRunnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        };

        String session = mShared.getString("session_id", null);
        long sessionExpired = mShared.getLong("session_expired", 0);
        if (session != null && System.currentTimeMillis() < sessionExpired) {
            getSession(false, session, sessionExpired);
        } else
            new ObservableApiCall(this, Endpoint.CreateSession).addObserver(this);
        new ObservableApiCall(this, Endpoint.Ping).addObserver(this);
    }

    private void parsePing(String res) {
        String regex = "(\\w+\\s+\\(+.+\\))+\\s+(\\[.*])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(res);

        String s = null;
        while (matcher.find()) {
            s = matcher.group(1) + " " + matcher.group(2);
        }
        if (s == null)
            mPatch.setText(getString(R.string.api_maintenance));
        else
            mPatch.setText(s);
    }

    private void storeSession(boolean isRequest, String session, long time) {
        if (isRequest) {
            SharedPreferences.Editor edit = mShared.edit();

            edit.putString("session_id", session);
            edit.putLong("session_expired", time + 900000);
            edit.apply();
        }
        PaladinSession.getInstance().setSession(session, time + 900000);
    }

    private void getSession(boolean isRequest, String res, long time) {
        if (isRequest) {
            String session = ApiParser.parseNewSession(res);
            if (session != null) {
                storeSession(true, session, time);
                mStatus.setText(getString(R.string.api_status_on));
                if (Champion.countData() == 0)
                    new ObservableApiCall(this, Endpoint.GetChampions, "1").addObserver(this);
                else
                    onRequestsFinished();
            } else {
                mStatus.setTextColor(ContextCompat.getColor(this, R.color.red));
                mStatus.setText(getString(R.string.api_status_off));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new ObservableApiCall(SplashScreen.this, Endpoint.CreateSession).addObserver(SplashScreen.this);
                    }
                }, 500);
            }
        } else {
            storeSession(false, res, time);
            mStatus.setText(getString(R.string.api_status_on));
            if (Champion.countData() == 0)
                new ObservableApiCall(this, Endpoint.GetChampions, "1").addObserver(this);
            onRequestsFinished();
        }
    }

    private void onRequestsFinished() {
        if (Item.countData() == 0)
            new ObservableApiCall(this, Endpoint.GetItems, "1").addObserver(this);
        else
            mHandler.postDelayed(mActivityRunnable, 5000);
    }

    @Override
    public void update(Observable o, final Object arg) {
        final ObservableApiCall client = (ObservableApiCall) o;
        Log.d("myType", arg.toString());

        switch ((String) arg) {
            case Endpoint.Ping:
                parsePing(client.getResult());
                break;
            case Endpoint.CreateSession:
                getSession(true, client.getResult(), System.currentTimeMillis());
                break;
            case Endpoint.GetChampions:
                ApiParser.storeChampions(client.getResult(), this);
                break;
            case Endpoint.GetItems:
                ApiParser.storeItems(client.getResult(), this);
                break;
        }
    }

    @Override
    public void onStored(String type) {
        switch (type) {
            case Endpoint.GetChampions:
                onRequestsFinished();
                break;
            case Endpoint.GetItems:
                onRequestsFinished();
                break;
        }
    }
}
