package com.stats.champions.paladins.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.stats.champions.paladins.R;
import com.stats.champions.paladins.ui.activity.MainActivity;
import com.stats.champions.paladins.ui.adapter.HistoryPlayerAdapter;
import com.stats.champions.paladins.api.ApiParser;
import com.stats.champions.paladins.api.Endpoint;
import com.stats.champions.paladins.api.ObservableApiCall;
import com.stats.champions.paladins.model.Player;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

public class FragmentSearchPlayer extends Fragment implements View.OnClickListener,
        Observer, ApiParser.OnDataStored, HistoryPlayerAdapter.OnPlayerClickHappened {

    @BindView(R.id.layout_no_found)
    View mNoFound;

    @BindView(R.id.recycler_players)
    RecyclerView mRecycler;

    @BindView(R.id.search_edit)
    EditText mSearch;

    @BindView(R.id.done_search)
    ImageView mSearchDone;

    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;

    private ProgressDialog mProgress;

    private MainActivity mContext;
    private LinearLayoutManager mLayoutManager;
    private HistoryPlayerAdapter mAdapter;
    private ArrayList<Player> mPlayerList;
    private boolean isFirstLaunch;
    private int mPlayerId;

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
        isFirstLaunch = true;
        mPlayerList = new ArrayList<>();
        mContext.getSupportActionBar().setTitle("Search a Player");
        mContext.displayArrowOrDrawer(false);

        mProgress = new ProgressDialog(mContext, R.style.ProgressDialogStyle);
        mProgress.setMessage(mContext.getString(R.string.get_player));
        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mLayoutManager = new LinearLayoutManager(mContext);
        mAdapter = new HistoryPlayerAdapter(this, mContext, mPlayerList);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);

        mSearchDone.setOnClickListener(this);
        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == IME_ACTION_DONE) {
                    searchPlayer();
                    return true;
                }
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
        if (isFirstLaunch) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateToolbarBehaviour();
                }
            }, 500);
        } else
            updateToolbarBehaviour();

    }

    private void searchPlayer() {
        String player = mSearch.getText().toString();
        if (!player.equals("")) {
            mProgress.show();
            new ObservableApiCall(getActivity(), Endpoint.GetPlayer, player).addObserver(this);
            mSearch.setText("");
            mSearch.clearFocus();
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mSearch.getApplicationWindowToken(), 0);
        }
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
                mPlayerId = ApiParser.storePlayer(client.getResult(), this);
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
                        mProgress.cancel();
                        if (mPlayerId != -1)
                            onPlayerClicked(null);
                        break;
                }
            }
        });
    }


    public void updateToolbarBehaviour() {
        Log.d("myListVisible", (mLayoutManager.findLastCompletelyVisibleItemPosition() == mPlayerList.size() - 1) + "");
        if (mLayoutManager.findLastCompletelyVisibleItemPosition() == mPlayerList.size() - 1) {
            ((MainActivity) getActivity()).turnOffToolbarScrolling();
        } else {
            ((MainActivity) getActivity()).turnOnToolbarScrolling();
        }
    }

    @Override
    public void onPlayerClicked(String name) {
        if (name != null) {
            mProgress.show();
            new ObservableApiCall(getActivity(), Endpoint.GetPlayer, name).addObserver(this);
            return;
        }
        mContext.expandAppBar();
        FragmentTransaction ft = mContext.getSupportFragmentManager().beginTransaction();

        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        ft.replace(R.id.container, FragmentPlayerContainer.newInstance(mPlayerId,
                mRadioGroup.getCheckedRadioButtonId() == R.id.summary_radio));
        ft.addToBackStack("HistoryPlayer");

        ft.commit();
    }
}
