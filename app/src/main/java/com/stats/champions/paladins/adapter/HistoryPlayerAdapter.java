package com.stats.champions.paladins.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stats.champions.paladins.R;
import com.stats.champions.paladins.model.Player;

import java.util.ArrayList;

public class HistoryPlayerAdapter extends RecyclerView.Adapter<HistoryPlayerAdapter.ViewHolder> {

    private ArrayList<Player> mList;

    public HistoryPlayerAdapter(ArrayList<Player> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simple_lone_text, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Player player = mList.get(position);

        holder.mText.setText(player.getName() + " - Level " + player.getLevel());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mText;


        ViewHolder(View itemView) {
            super(itemView);

            mText = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
