package com.stats.champions.paladins.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.stats.champions.paladins.R;
import com.stats.champions.paladins.api.Endpoint;
import com.stats.champions.paladins.api.ObservableApiCall;
import com.stats.champions.paladins.firebase.FirebaseAnalyticsEvents;
import com.stats.champions.paladins.ui.fragment.FragmentSearchPlayer;
import com.webianks.easy_feedback.EasyFeedback;

import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Observer, View.OnClickListener {

    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @BindView(R.id.nav_view)
    NavigationView mNav;

    private View.OnClickListener mListener;
    private ActionBarDrawerToggle mToggle;
    private EasyFeedback mFeedBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        FirebaseAnalyticsEvents.sendActivityEvent(this, this.getTitle().toString(), "MainActivity");

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, FragmentSearchPlayer.newInstance())
                .commit();

        mFeedBack = new EasyFeedback.Builder(this)
                .withEmail(getString(R.string.email_me)).withSystemInfo().build();
        setSupportActionBar(mToolbar);
        ViewCompat.setElevation(mAppBarLayout, 0);
        initViews();
    }

    private void initViews() {

        mToggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        mListener = mToggle.getToolbarNavigationClickListener();

        mNav.setNavigationItemSelectedListener(this);
    }

    public void displayArrowOrDrawer(boolean isReturnNeeded) {
        if (isReturnNeeded) {
            mToggle.setDrawerIndicatorEnabled(false);
            mToggle.setToolbarNavigationClickListener(this);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            mToggle.setDrawerIndicatorEnabled(true);
            mToggle.setToolbarNavigationClickListener(mListener);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            mToggle.syncState();
        }
    }

    public void expandAppBar() {
        mAppBarLayout.setExpanded(true);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            getSupportFragmentManager().popBackStack();
        } else if (id == R.id.action_about) {

        } else if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_champions) {
            new ObservableApiCall(this, Endpoint.GetMatchHistory, "Nitratz").addObserver(MainActivity.this);
        } else if (id == R.id.nav_player) {
            new ObservableApiCall(this, Endpoint.GetPlayer, "Lzanamie").addObserver(MainActivity.this);
        } else if (id == R.id.nav_feedback) {
            mFeedBack.start();
        } else if (id == R.id.nav_about) {

        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void update(Observable o, Object arg) {
        Log.d("myResult", ((ObservableApiCall) o).getResult());
    }

    public void turnOffToolbarScrolling() {
        AppBarLayout.LayoutParams toolbarLayoutParams = (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
        toolbarLayoutParams.setScrollFlags(0);
        mToolbar.setLayoutParams(toolbarLayoutParams);

        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        appBarLayoutParams.setBehavior(null);
        mAppBarLayout.setLayoutParams(appBarLayoutParams);
    }

    public void turnOnToolbarScrolling() {
        AppBarLayout.LayoutParams toolbarLayoutParams = (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
        toolbarLayoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        mToolbar.setLayoutParams(toolbarLayoutParams);

        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        appBarLayoutParams.setBehavior(new AppBarLayout.Behavior());
        mAppBarLayout.setLayoutParams(appBarLayoutParams);
    }

    @Override
    public void onClick(View v) {
        getSupportFragmentManager().popBackStack();
    }
}
