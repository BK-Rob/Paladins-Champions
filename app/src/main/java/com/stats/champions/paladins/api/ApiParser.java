package com.stats.champions.paladins.api;

import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.stats.champions.paladins.model.Champion;
import com.stats.champions.paladins.model.Item;
import com.stats.champions.paladins.model.Player;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.uk.rushorm.core.RushCallback;
import co.uk.rushorm.core.RushCore;

public class ApiParser {

    public interface OnDataStored {
        void onStored(String type);
    }

    public static String parseNewSession(String res) {
        try {
            Log.d("myParser", "Parsing Session...");
            JSONObject obj = new JSONObject(res);
            if (obj.getString("ret_msg").equals("Approved")) {
                return obj.getString("session_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
        return null;
    }

    public static boolean storeChampions(String res, final OnDataStored listener) {
        try {
            Log.d("myParser", "Parsing Champions...");
            JSONObject obj = new JSONArray(res).getJSONObject(0);
            if (obj.getString("Ability1").equals("null"))
                return false;
            final ArrayList<Champion> champions = new Gson().fromJson(res,
                    new TypeToken<ArrayList<Champion>>() {
                    }.getType());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RushCore.getInstance().deleteAll(Champion.class);
                    RushCore.getInstance().save(champions, new RushCallback() {
                        @Override
                        public void complete() {
                            listener.onStored(Endpoint.GetChampions);
                        }
                    });
                }
            }).start();
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
        return false;
    }

    public static boolean storePlayer(String res, final OnDataStored listener) {
        Log.d("myParser", "Parsing player...");
        if (res.equals("[]"))
            return false;
        JsonArray array = new JsonParser().parse(res).getAsJsonArray();
        JsonObject obj = (JsonObject) array.get(0);

        final Player player = new Gson().fromJson(obj, Player.class);
        if (player.getName().equals("null"))
            return false;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ArrayList<Player> playerList = Player.loadDataByName("\"" + player.getName() + "\"");
                    if (playerList.size() != 0)
                        Player.delete(playerList.get(0));
                    RushCore.getInstance().save(player, new RushCallback() {
                        @Override
                        public void complete() {
                            listener.onStored(Endpoint.GetPlayer);
                        }
                    });
                }
            }).start();
        return true;
    }

    public static boolean storeItems(String res, final OnDataStored listener) {
        try {
            Log.d("myParser", "Parsing Items...");
            JSONObject obj = new JSONArray(res).getJSONObject(0);
            if (obj.getString("Description").equals("null"))
                return false;

            final ArrayList<Item> items = new Gson().fromJson(res,
                    new TypeToken<ArrayList<Item>>() {
                    }.getType());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RushCore.getInstance().deleteAll(Item.class);
                    RushCore.getInstance().save(items, new RushCallback() {
                        @Override
                        public void complete() {
                            listener.onStored(Endpoint.GetItems);
                        }
                    });
                }
            }).start();
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
        return false;
    }
}
