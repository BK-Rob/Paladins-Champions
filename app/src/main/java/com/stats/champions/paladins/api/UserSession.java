package com.stats.champions.paladins.api;

/**
 * Created by Backs on 27/04/2017.
 */

public class UserSession {

    public final String DEV_ID = "2008";
    public final String AUTH_KEY = "AE978BA9199F41D1A6525FFD8831821A";
    private String mSession = null;

    private static UserSession INSTANCE;

    public static UserSession getInstance() {
        if (INSTANCE == null)
            INSTANCE = new UserSession();
        return INSTANCE;
    }

    private UserSession() {

    }

    public String getSession() {
        return mSession;
    }

    public void setSession(String session) {
        mSession = session;
    }
}
