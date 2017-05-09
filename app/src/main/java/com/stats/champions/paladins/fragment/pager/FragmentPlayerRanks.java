package com.stats.champions.paladins.fragment.pager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stats.champions.paladins.R;
import com.stats.champions.paladins.api.Endpoint;
import com.stats.champions.paladins.api.ObservableApiCall;
import com.stats.champions.paladins.model.Player;

import java.util.Observable;
import java.util.Observer;

import butterknife.ButterKnife;

public class FragmentPlayerRanks extends Fragment implements Observer {

    private Activity mContext;
    private Player mPlayer;

    public static FragmentPlayerRanks newInstance(String name) {
        Bundle args = new Bundle();

        args.putString("player_name", name);
        FragmentPlayerRanks fragment = new FragmentPlayerRanks();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_player, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, v);
        String name = getArguments().getString("player_name");
        mPlayer = Player.loadDataByName(name).get(0);

        new ObservableApiCall(mContext, Endpoint.GetChampionRanks, mPlayer.getName()).addObserver(this);
        return v;
    }

    @Override
    public void update(Observable o, Object arg) {
        final ObservableApiCall client = (ObservableApiCall) o;
        Log.d("myType", arg.toString());

        switch ((String) arg) {
            case Endpoint.GetChampionRanks:
                break;
        }
    }
}
