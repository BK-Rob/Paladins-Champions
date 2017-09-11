package com.stats.champions.paladins.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stats.champions.paladins.R;
import com.stats.champions.paladins.activity.MainActivity;
import com.stats.champions.paladins.adapter.PlayerStatsAdapter;
import com.stats.champions.paladins.api.ApiParser;
import com.stats.champions.paladins.api.Endpoint;
import com.stats.champions.paladins.api.ObservableApiCall;
import com.stats.champions.paladins.model.Player;

import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentPlayerContainer extends Fragment {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private PlayerStatsAdapter mAdapter;
    private MainActivity mActivity;

    public static FragmentPlayerContainer newInstance(int id, boolean isSummary) {
        Bundle args = new Bundle();

        args.putBoolean("type_checked", isSummary);
        args.putInt("player_id", id);
        FragmentPlayerContainer fragment = new FragmentPlayerContainer();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_player_container, container, false);
        mActivity = (MainActivity) getActivity();
        ButterKnife.bind(this, v);

        Bundle b = getArguments();
        int id = b.getInt("player_id");
        boolean isSummary = b.getBoolean("type_checked");
        Player p = Player.loadDataById(id).get(0);

        mActivity.displayArrowOrDrawer(true);
        mActivity.getSupportActionBar().setTitle(p.getName());
        mAdapter = new PlayerStatsAdapter(mActivity, getChildFragmentManager(), id);

        mViewPager.setOffscreenPageLimit(1);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mAdapter);

        if (isSummary)
            mViewPager.setCurrentItem(1);
        else
            mViewPager.setCurrentItem(2);

        return v;
    }
}
