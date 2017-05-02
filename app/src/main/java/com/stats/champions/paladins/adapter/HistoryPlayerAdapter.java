package com.stats.champions.paladins.adapter;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stats.champions.paladins.R;
import com.stats.champions.paladins.fragment.FragmentPlayerStats;
import com.stats.champions.paladins.model.Player;

import java.util.ArrayList;

public class HistoryPlayerAdapter extends RecyclerView.Adapter<HistoryPlayerAdapter.ViewHolder>
        implements View.OnClickListener {

    private Context mContext;

    private ArrayList<Player> mList;

    public HistoryPlayerAdapter(Context context, ArrayList<Player> list) {
        mContext = context;
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
        TextView mText;
        View mClicker;


        ViewHolder(View itemView) {
            super(itemView);

            mClicker = itemView.findViewById(R.id.player);
            mText = (TextView) itemView.findViewById(R.id.text);
        }
    }

    @Override
    public void onClick(View v) {
        int pos = (Integer) v.getTag();
        Player player = mList.get(pos);
        String id = String.valueOf(player.getmId());

        FragmentTransaction ft = ((AppCompatActivity) mContext).getSupportFragmentManager()
                .beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        ft.replace(R.id.container, FragmentPlayerStats.newInstance(id));
        ft.addToBackStack("HistoryPlayer");
        ft.commit();
    }
}
