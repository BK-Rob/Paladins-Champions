package com.stats.champions.paladins.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.stats.champions.paladins.R;
import com.stats.champions.paladins.fragment.pager.FragmentPlayerHistory;
import com.stats.champions.paladins.fragment.pager.FragmentPlayerRanks;
import com.stats.champions.paladins.fragment.pager.FragmentPlayerSummary;

public class PlayerStatsAdapter extends FragmentPagerAdapter {

    private Context mContext;

    private FragmentPlayerSummary mSummary;
    private FragmentPlayerHistory mHistory;
    private FragmentPlayerRanks mRanks;

    public PlayerStatsAdapter(Context context, FragmentManager fm, int playerId) {
        super(fm);
        mContext = context;

        mSummary = FragmentPlayerSummary.newInstance(playerId);
        mHistory = FragmentPlayerHistory.newInstance(playerId);
        mRanks = FragmentPlayerRanks.newInstance(playerId);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = mRanks;
                break;
            case 1:
                frag = mSummary;
                break;
            case 2:
                frag = mHistory;
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                title = mContext.getString(R.string.pager_ranks);
                break;
            case 1:
                title = mContext.getString(R.string.pager_summary);
                break;
            case 2:
                title = mContext.getString(R.string.pager_history);
                break;
        }
        return title;
    }
}
