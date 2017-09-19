package com.stats.champions.paladins.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stats.champions.paladins.R;
import com.stats.champions.paladins.model.ChampionRank;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerRanksAdapter extends RecyclerView.Adapter<PlayerRanksAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ChampionRank> mList;

    public PlayerRanksAdapter(Context context, ArrayList<ChampionRank> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_player_ranks, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChampionRank champion = mList.get(position);

        if (champion.getChampion().equals("Willo"))
            Glide.with(mContext).load(R.drawable.willo).into(holder.mChampionImage);
        else if (champion.getChampion().equals("Seris"))
            Glide.with(mContext).load(R.drawable.seris).into(holder.mChampionImage);
        else
            Glide.with(mContext).load(champion.getChampionIconUrl()).into(holder.mChampionImage);
        holder.mChampionName.setText(champion.getChampion());
        holder.mMasteryLevel.setText(String.valueOf(champion.getRank()));

        float kda = (float) (champion.getKills() + champion.getAssists()) / champion.getDeaths();
        DecimalFormat mKDAFormat = new DecimalFormat("##.0");
        holder.mKDA.setText(mKDAFormat.format(kda));

        int total = champion.getWins() + champion.getLosses();
        float winrate = (float) (champion.getWins() * 100) / total;
        DecimalFormat mFormat = new DecimalFormat("##.00");
        holder.mWinrate.setText(mFormat.format(winrate) + "%");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.champion_image) ImageView mChampionImage;
        @BindView(R.id.champion_name) TextView mChampionName;
        @BindView(R.id.mastery_level) TextView mMasteryLevel;
        @BindView(R.id.champion_winrate) TextView mWinrate;
        @BindView(R.id.champion_kda) TextView mKDA;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
