package com.stats.champions.paladins.ui.fragment.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stats.champions.paladins.R;

public class FragmentPlayerHistory extends Fragment {

    public static FragmentPlayerHistory newInstance(int id) {
        Bundle args = new Bundle();

        args.putInt("player_id", id);
        FragmentPlayerHistory fragment = new FragmentPlayerHistory();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_player, container, false);

        return v;
    }
}
