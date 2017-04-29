package com.stats.champions.paladins.api;

public class UserSession {

    final String DEV_ID = "2008";
    final String AUTH_KEY = "AE978BA9199F41D1A6525FFD8831821A";
    private String mSession = null;
    private long mSessionExpired;

    private static UserSession INSTANCE;

    public static UserSession getInstance() {
        if (INSTANCE == null)
            INSTANCE = new UserSession();
        return INSTANCE;
    }

    private UserSession() {
    }

    public String getSession() {
        if (System.currentTimeMillis() > mSessionExpired)
            return null;
        return mSession;
    }

    public void setSession(String session, long sessionExpired) {
        mSession = session;
        mSessionExpired = sessionExpired;
    }
}
