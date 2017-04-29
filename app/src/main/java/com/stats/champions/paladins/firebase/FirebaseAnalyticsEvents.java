package com.stats.champions.paladins.firebase;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class FirebaseAnalyticsEvents {

    public static void sendActivityEvent(Context context, String name, String type) {
        Bundle bundle = new Bundle();
        bundle.putString("ActivityName",name);
        bundle.putString(com.google.firebase.analytics.FirebaseAnalytics.Param.CONTENT_TYPE, "STARTUP");
        FirebaseAnalytics.getInstance(context).logEvent(com.google.firebase.analytics.FirebaseAnalytics.Event.APP_OPEN, bundle);
    }
}
