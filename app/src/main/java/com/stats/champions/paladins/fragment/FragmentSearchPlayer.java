package com.stats.champions.paladins.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.stats.champions.paladins.R;
import com.stats.champions.paladins.activity.MainActivity;
import com.stats.champions.paladins.adapter.HistoryPlayerAdapter;
import com.stats.champions.paladins.api.ApiParser;
import com.stats.champions.paladins.api.Endpoint;
import com.stats.champions.paladins.api.ObservableApiCall;
import com.stats.champions.paladins.model.Champion;
import com.stats.champions.paladins.model.Player;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

public class FragmentSearchPlayer extends Fragment implements View.OnClickListener, Observer, ApiParser.OnDataStored {

    @BindView(R.id.layout_no_found)
    View mNoFound;

    @BindView(R.id.recycler_players)
    RecyclerView mRecycler;

    @BindView(R.id.search_edit)
    EditText mSearch;

    @BindView(R.id.done_search)
    ImageView mSearchDone;

    private MainActivity mContext;
    private LinearLayoutManager mLayoutManager;
    private HistoryPlayerAdapter mAdapter;
    private ArrayList<Player> mPlayerList;

    public static FragmentSearchPlayer newInstance() {
        Bundle args = new Bundle();

        FragmentSearchPlayer fragment = new FragmentSearchPlayer();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_player, container, false);
        mContext = (MainActivity) getActivity();
        ButterKnife.bind(this, v);
        mPlayerList = new ArrayList<>();
        mContext.getSupportActionBar().setTitle("Search a Player");
        mContext.displayArrowOrDrawer(false);

        mLayoutManager = new LinearLayoutManager(mContext);
        mAdapter = new HistoryPlayerAdapter(mContext, mPlayerList);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);

        mSearchDone.setOnClickListener(this);
        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == IME_ACTION_DONE)
                    searchPlayer();
                return false;
            }
        });

        if (Player.countData() != 0)
            populateRecycler();
        return v;
    }

    private void populateRecycler() {
        mNoFound.setVisibility(View.GONE);
        mPlayerList.clear();
        mPlayerList.addAll(Player.loadAllData());
        mAdapter.notifyDataSetChanged();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateToolbarBehaviour();
            }
        }, 100);

    }

    private void searchPlayer() {
        String player = mSearch.getText().toString();
        if (!player.equals(""))
            new ObservableApiCall(getActivity(), Endpoint.GetPlayer, player).addObserver(this);
        mSearch.setText("");
        mSearch.clearFocus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearch.getApplicationWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.done_search:
                searchPlayer();
                break;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        final ObservableApiCall client = (ObservableApiCall) o;

        switch ((String) arg) {
            case Endpoint.GetPlayer:
                ApiParser.storePlayer(client.getResult(), this);
                break;
        }
    }

    @Override
    public void onStored(final String type) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (type) {
                    case Endpoint.GetPlayer:
                        populateRecycler();
                        break;
                }
            }
        });
    }


    public void updateToolbarBehaviour() {
        if (mLayoutManager.findLastCompletelyVisibleItemPosition() == mPlayerList.size() - 1) {
            ((MainActivity) getActivity()).turnOffToolbarScrolling();
        } else {
            ((MainActivity) getActivity()).turnOnToolbarScrolling();
        }
    }
}
