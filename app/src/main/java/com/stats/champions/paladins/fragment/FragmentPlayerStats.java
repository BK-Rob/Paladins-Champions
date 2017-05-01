package com.stats.champions.paladins.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stats.champions.paladins.R;

public class FragmentPlayerStats extends Fragment {

    public static FragmentPlayerStats newInstance() {
        Bundle args = new Bundle();

        FragmentPlayerStats fragment = new FragmentPlayerStats();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_player_stats, container, false);

        return v;
    }
}
