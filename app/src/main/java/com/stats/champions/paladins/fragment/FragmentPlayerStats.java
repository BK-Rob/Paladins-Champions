package com.stats.champions.paladins.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stats.champions.paladins.R;
import com.stats.champions.paladins.activity.MainActivity;
import com.stats.champions.paladins.model.Player;

public class FragmentPlayerStats extends Fragment {

    private MainActivity mActivity;
    private Player mPlayer;

    public static FragmentPlayerStats newInstance(String id) {
        Bundle args = new Bundle();

        args.putString("player_id", id);
        FragmentPlayerStats fragment = new FragmentPlayerStats();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_player_stats, container, false);
        mActivity = (MainActivity) getActivity();


        String id = getArguments().getString("player_id");
        mPlayer = Player.loadDataById(id).get(0);

        mActivity.displayArrowOrDrawer(true);
        mActivity.getSupportActionBar().setTitle(mPlayer.getName());

        return v;
    }
}
