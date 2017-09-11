package com.stats.champions.paladins.fragment.pager;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stats.champions.paladins.R;
import com.stats.champions.paladins.adapter.PlayerRanksAdapter;
import com.stats.champions.paladins.api.Endpoint;
import com.stats.champions.paladins.api.ObservableApiCall;
import com.stats.champions.paladins.model.Champion;
import com.stats.champions.paladins.model.ChampionRank;
import com.stats.champions.paladins.model.Item;
import com.stats.champions.paladins.model.Player;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentPlayerRanks extends Fragment implements Observer {

    @BindView(R.id.champion_ranks)
    RecyclerView mRecycler;

    private LinearLayoutManager mLayoutManager;
    private PlayerRanksAdapter mAdapter;
    private ArrayList<ChampionRank> mList;
    private ArrayList<Champion> mChampionList;
    private Activity mContext;
    private Player mPlayer;

    public static FragmentPlayerRanks newInstance(int id) {
        Bundle args = new Bundle();

        args.putInt("player_id", id);
        FragmentPlayerRanks fragment = new FragmentPlayerRanks();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_player_ranks, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, v);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecycler.setLayoutManager(mLayoutManager);
        int id = getArguments().getInt("player_id");
        mPlayer = Player.loadDataById(id).get(0);
        mChampionList = Champion.loadAllData();

        new ObservableApiCall(mContext, Endpoint.GetChampionRanks, mPlayer.getName()).addObserver(this);
        return v;
    }

    private void setChampionPicture() {
        for (ChampionRank rank : mList) {
            ArrayList<Champion> champs = Champion.loadDataById(rank.getChampionId());
            if (champs.size() > 0) {
                rank.setChampionIconUrl(champs.get(0).getChampionIconURL());
            }
        }
    }

    private void parseChampionRanks(String res) {
        if (res.equals("[]") || res.contains("Invalid signature.")) {
            Toast.makeText(mContext, "An error has occured.", Toast.LENGTH_LONG).show();
            return;
        }
        mList = new Gson().fromJson(res, new TypeToken<ArrayList<ChampionRank>>() {}.getType());
        if (mList.size() > 0) {
            setChampionPicture();
            mAdapter = new PlayerRanksAdapter(mContext, mList);
            mRecycler.setAdapter(mAdapter);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        final ObservableApiCall client = (ObservableApiCall) o;
        Log.d("myType", arg.toString());

        switch ((String) arg) {
            case Endpoint.GetChampionRanks:
                parseChampionRanks(client.getResult());
                break;
        }
    }
}
