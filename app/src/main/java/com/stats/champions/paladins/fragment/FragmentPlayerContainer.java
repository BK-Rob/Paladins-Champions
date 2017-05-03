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

public class FragmentPlayerContainer extends Fragment implements Observer, ApiParser.OnDataStored {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private PlayerStatsAdapter mAdapter;
    private MainActivity mActivity;
    private Player mPlayer;

    public static FragmentPlayerContainer newInstance(String name, boolean isSummary) {
        Bundle args = new Bundle();

        args.putBoolean("type_checked", isSummary);
        args.putString("player_name", name);
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
        String name = b.getString("player_name");
        boolean isSummary = b.getBoolean("type_checked");
        new ObservableApiCall(mActivity, Endpoint.GetPlayer, name).addObserver(this);

        mActivity.displayArrowOrDrawer(true);
        mActivity.getSupportActionBar().setTitle(name);
        mAdapter = new PlayerStatsAdapter(mActivity, getChildFragmentManager(), name);

        mViewPager.setOffscreenPageLimit(1);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mAdapter);

        if (isSummary)
            mViewPager.setCurrentItem(1);
        else
            mViewPager.setCurrentItem(2);

        return v;
    }


    @Override
    public void update(Observable o, Object arg) {
        final ObservableApiCall client = (ObservableApiCall) o;
        Log.d("myType", arg.toString());

        switch ((String) arg) {
            case Endpoint.GetPlayer:
                ApiParser.storePlayer(client.getResult(), this);
                break;
        }
    }

    @Override
    public void onStored(String type) {

    }
}
