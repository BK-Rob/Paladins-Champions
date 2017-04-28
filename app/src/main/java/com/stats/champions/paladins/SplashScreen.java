package com.stats.champions.paladins;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stats.champions.paladins.api.Endpoint;
import com.stats.champions.paladins.api.ObservableApiCall;
import com.stats.champions.paladins.api.UserSession;
import com.stats.champions.paladins.database.DataBaseUtil;
import com.stats.champions.paladins.model.Champion;
import com.stats.champions.paladins.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.uk.rushorm.core.RushCore;

public class SplashScreen extends Activity implements Observer {

    @BindView(R.id.status)
    TextView mStatus;

    @BindView(R.id.patch)
    TextView mPatch;

    @BindView(R.id.spin_kit)
    SpinKitView mSpinKit;

    private Handler mHandler;
    private Runnable mActivityRunnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        ButterKnife.bind(this);
        FirebaseApp.initializeApp(this);

        Bundle bundle = new Bundle();
        bundle.putString("ActivityName", this.getTitle().toString());
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "STARTUP");
        FirebaseAnalytics.getInstance(SplashScreen.this).logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle);

        new ObservableApiCall(this, Endpoint.Ping).addObserver(this);
        new ObservableApiCall(this, Endpoint.CreateSession).addObserver(this);

        setRandomLoading();
        mHandler = new Handler();
        mActivityRunnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        };
    }

    private void setRandomLoading() {
        Drawable[] styleArray = new Drawable[4];
        Random rand = new Random();

        styleArray[0] = new WanderingCubes();
        styleArray[1] = new ChasingDots();
        styleArray[2] = new FoldingCube();
        styleArray[3] = new DoubleBounce();

        mSpinKit.setIndeterminateDrawable(styleArray[rand.nextInt(4)]);
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

    private void parseNewSession(String res) {
        try {
            JSONObject obj = new JSONObject(res);
            if (obj.getString("ret_msg").equals("Approved")) {
                UserSession.getInstance().setSession(obj.getString("session_id"));
                mStatus.setText("API Status: OK");
                if (DataBaseUtil.countData(Champion.class) == 0)
                    new ObservableApiCall(this, Endpoint.GetChampions, "1").addObserver(SplashScreen.this);
                else
                    onChampionLoaded();
            } else {
                mStatus.setTextColor(ContextCompat.getColor(SplashScreen.this, R.color.red));
                mStatus.setText("API Status: OFF");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    private void onChampionLoaded() {
        new ObservableApiCall(this, Endpoint.GetItems, "1").addObserver(this);
    }

    private void storeChampions(String res) {
        try {
            JSONObject obj = new JSONArray(res).getJSONObject(0);
            if (obj.getString("Ability1").equals("null"))
                return;

            ArrayList<Champion> champions = new Gson().fromJson(res,
                    new TypeToken<ArrayList<Champion>>() {
                    }.getType());
            RushCore.getInstance().deleteAll(Champion.class);
            RushCore.getInstance().save(champions);
        } catch (JSONException e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
        onChampionLoaded();
    }

    private void storeItems(String res) {
        try {
            JSONObject obj = new JSONArray(res).getJSONObject(0);
            if (obj.getString("Description").equals("null"))
                return;

            ArrayList<Item> items = new Gson().fromJson(res,
                    new TypeToken<ArrayList<Item>>() {
                    }.getType());
            RushCore.getInstance().deleteAll(Item.class);
            RushCore.getInstance().save(items);
        } catch (JSONException e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }

    }

    @Override
    public void update(Observable o, final Object arg) {
        final ObservableApiCall client = (ObservableApiCall) o;
        Log.d("myType", arg.toString());

        switch ((String) arg) {
            case Endpoint.Ping:
                parseLine(client.getResult());
                break;
            case Endpoint.CreateSession:
                Log.d("WaitWhat", "Lolz");
                parseNewSession(client.getResult());
                break;
            case Endpoint.GetChampions:
                storeChampions(client.getResult());
                break;
            case Endpoint.GetItems:
                storeItems(client.getResult());
                break;
        }
    }
}
