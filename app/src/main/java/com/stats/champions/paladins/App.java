package com.stats.champions.paladins;

import android.app.Application;

import com.stats.champions.paladins.api.Champion;

import java.util.ArrayList;
import java.util.List;

import co.uk.rushorm.android.AndroidInitializeConfig;
import co.uk.rushorm.android.RushAndroid;
import co.uk.rushorm.core.Rush;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        List<Class<? extends Rush>> classes = new ArrayList<>();
        classes.add(Champion.class);

        AndroidInitializeConfig androidInitializeConfig = new AndroidInitializeConfig(getApplicationContext(), classes);
        RushAndroid.initialize(androidInitializeConfig);
    }

}