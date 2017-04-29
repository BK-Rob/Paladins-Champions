package com.stats.champions.paladins.api;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stats.champions.paladins.model.Champion;
import com.stats.champions.paladins.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.uk.rushorm.core.RushCore;

public class ApiParser {

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

    public static boolean storeChampions(String res) {
        try {
            Log.d("myParser", "Parsing Champions...");
            JSONObject obj = new JSONArray(res).getJSONObject(0);
            if (obj.getString("Ability1").equals("null"))
                return false;
            ArrayList<Champion> champions = new Gson().fromJson(res,
                    new TypeToken<ArrayList<Champion>>() {
                    }.getType());
            RushCore.getInstance().deleteAll(Champion.class);
            RushCore.getInstance().save(champions);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
        return false;
    }

    public static boolean storeItems(String res) {
        try {
            Log.d("myParser", "Parsing Items...");
            JSONObject obj = new JSONArray(res).getJSONObject(0);
            if (obj.getString("Description").equals("null"))
                return false;

            ArrayList<Item> items = new Gson().fromJson(res,
                    new TypeToken<ArrayList<Item>>() {
                    }.getType());
            RushCore.getInstance().deleteAll(Item.class);
            RushCore.getInstance().save(items);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
        return false;
    }
}
