package com.stats.champions.paladins.ui.fragment.pager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;
import com.stats.champions.paladins.R;
import com.stats.champions.paladins.api.Endpoint;
import com.stats.champions.paladins.api.ObservableApiCall;
import com.stats.champions.paladins.model.Player;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentPlayerSummary extends Fragment implements Observer {

    @BindView(R.id.player_name)
    TextView mPlayerName;
    @BindView(R.id.level_server)
    TextView mLevelServer;
    @BindView(R.id.nb_leaves)
    TextView mLeaves;

    @BindView(R.id.status_color)
    ImageView mStatusColor;
    @BindView(R.id.status_text)
    TextView mStatusText;

    @BindView(R.id.nb_wins)
    TextView mNbWins;
    @BindView(R.id.nb_looses)
    TextView mNbLosses;
    @BindView(R.id.winrate)
    TextView mWinrate;
    @BindView(R.id.nb_achievements)
    TextView mNbAchievements;

    private Activity mContext;
    private Player mPlayer;

    public static FragmentPlayerSummary newInstance(int id) {
        Bundle args = new Bundle();

        args.putInt("player_id", id);
        FragmentPlayerSummary fragment = new FragmentPlayerSummary();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_player_summary, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, v);
        int id = getArguments().getInt("player_id");
        mPlayer = Player.loadDataById(id).get(0);

        mPlayerName.setText(mPlayer.getName());
        mLevelServer.setText("Level " + mPlayer.getLevel() + " - " + mPlayer.getRegion());
        mLeaves.setText("Leaves : " + mPlayer.getLeaves());
        mNbWins.setText(String.valueOf(mPlayer.getWins()));
        mNbLosses.setText(String.valueOf(mPlayer.getLosses()));
        int total = mPlayer.getWins() + mPlayer.getLosses();
        float winrate = (float) (mPlayer.getWins() * 100) / total;
        DecimalFormat mFormat = new DecimalFormat("##.00");
        mWinrate.setText(mFormat.format(winrate) + "%");
        mNbAchievements.setText(mPlayer.getTotalAchievements() + " / 43");

        new ObservableApiCall(mContext, Endpoint.GetPlayerStatus, mPlayer.getName()).addObserver(this);
        return v;
    }

    private void parseStatus(String res) {
        try {
            JSONArray array = new JSONArray(res);
            JSONObject obj = array.getJSONObject(0);
            if (obj.getString("ret_msg").equals("null")) {
                String status = obj.getString("status_string");
                mStatusText.setText(status);
                if (status.equals("Offline"))
                    mStatusColor.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_circle_red));
                else if (status.equals("In Game"))
                    mStatusColor.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_circle_orange));
                else
                    mStatusColor.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_circle_green));

            }
        } catch (JSONException e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        final ObservableApiCall client = (ObservableApiCall) o;
        Log.d("myType", arg.toString());

        switch ((String) arg) {
            case Endpoint.GetPlayerStatus:
                parseStatus(client.getResult());
                break;
            case Endpoint.GetFriends:
                break;
        }
    }
}
