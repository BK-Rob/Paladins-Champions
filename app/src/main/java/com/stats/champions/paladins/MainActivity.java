package com.stats.champions.paladins;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stats.champions.paladins.api.Champion;
import com.stats.champions.paladins.api.RestClient;
import com.stats.champions.paladins.api.RestClient.REQUEST_TYPE;
import com.stats.champions.paladins.api.UserSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import co.uk.rushorm.core.RushCallback;
import co.uk.rushorm.core.RushCore;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Observer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews(toolbar);
    }

    private void initViews(Toolbar toolbar) {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RestClient(REQUEST_TYPE.CONNEXION, "createsession").addObserver(MainActivity.this);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        } else if (id == R.id.nav_gallery) {
            new RestClient(REQUEST_TYPE.TEST, "testsession").addObserver(MainActivity.this);
        } else if (id == R.id.nav_slideshow) {
            new RestClient(REQUEST_TYPE.CHAMPIONS, "getchampions", "1").addObserver(MainActivity.this);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals(REQUEST_TYPE.CONNEXION)) {
            RestClient client = ((RestClient) o);
            String res = client.getResult();
            Log.d("myResult", res);
            try {
                JSONObject obj = new JSONObject(res);
                if (obj.getString("ret_msg").equals("Approved"))
                    UserSession.getInstance().setSession(obj.getString("session_id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (arg.equals(REQUEST_TYPE.TEST)) {
            RestClient client = ((RestClient) o);
            String res = client.getResult();
            Log.d("myResult", res);
        } else if (arg.equals(REQUEST_TYPE.PLAYER)) {
            RestClient client = ((RestClient) o);
            String res = client.getResult();
            Log.d("myResult", res);
        }
        else {
            RestClient client = ((RestClient) o);
            String res = client.getResult();
            Log.d("myResult", res);
            try {
                JSONObject obj = new JSONArray(res).getJSONObject(0);
                if (obj.getString("Ability1").equals("null"))
                    return;

                ArrayList<Champion> champions = new Gson().fromJson(res, new TypeToken<ArrayList<Champion>>(){}.getType());
                Champion t = champions.get(0);
                RushCore.getInstance().deleteAll(Champion.class);
                RushCore.getInstance().save(champions);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
