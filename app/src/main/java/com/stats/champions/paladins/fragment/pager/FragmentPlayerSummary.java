package com.stats.champions.paladins.fragment.pager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.crash.FirebaseCrash;
import com.stats.champions.paladins.R;
import com.stats.champions.paladins.api.Endpoint;
import com.stats.champions.paladins.api.ObservableApiCall;
import com.stats.champions.paladins.model.Player;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

public class FragmentPlayerSummary extends Fragment implements Observer {

    private Activity mContext;
    private Player mPlayer;

    public static FragmentPlayerSummary newInstance(String name) {
        Bundle args = new Bundle();

        args.putString("player_name", name);
        FragmentPlayerSummary fragment = new FragmentPlayerSummary();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_player_summary, container, false);
        mContext = getActivity();
        String name = getArguments().getString("player_name");
        mPlayer = Player.loadDataByName(name).get(0);

        new ObservableApiCall(mContext, Endpoint.GetPlayerStatus, name).addObserver(this);
        return v;
    }

    private void parseStatus(String res) {
        try {
            JSONArray array = new JSONArray(res);
            JSONObject obj = array.getJSONObject(0);
            String status = obj.getString("status_string");
        } catch (JSONException e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        final ObservableApiCall client = (ObservableApiCall) o;
        Log.d("myType", arg.toString());

        switch ((String) arg) {
            case Endpoint.GetPlayerStatus:
                parseStatus(client.getResult());
                break;
        }
    }
}
