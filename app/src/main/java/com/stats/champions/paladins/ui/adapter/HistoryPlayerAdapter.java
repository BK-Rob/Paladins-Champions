package com.stats.champions.paladins.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stats.champions.paladins.R;
import com.stats.champions.paladins.model.Player;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryPlayerAdapter extends RecyclerView.Adapter<HistoryPlayerAdapter.ViewHolder>
        implements View.OnClickListener {

    private Context mContext;
    private OnPlayerClickHappened mListener;

    private ArrayList<Player> mList;

    public HistoryPlayerAdapter(OnPlayerClickHappened listener, Context context, ArrayList<Player> list) {
        mContext = context;
        mListener = listener;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_player_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Player player = mList.get(position);

        holder.mText.setText(player.getName() + " - Level " + player.getLevel());
        holder.mClicker.setOnClickListener(this);
        holder.mClicker.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * VIEWHOLDER
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text) TextView mText;
        View mClicker;

        ViewHolder(View itemView) {
            super(itemView);
            mClicker = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void onClick(View v) {
        int pos = (Integer) v.getTag();
        Player player = mList.get(pos);

        mListener.onPlayerClicked(player.getName());
    }

    public interface OnPlayerClickHappened {
        void onPlayerClicked(String name);
    }
}
