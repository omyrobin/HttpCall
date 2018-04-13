package com.dcloud.live.http.header;

/**
 * Created by wubo on 2018/4/10.
 */

public class SessionManager {
    private static String session;

    private static long lastApiCallTime;

    public static void setSession(String session) {
        SessionManager.session = session;
    }

    public static void setLastApiCallTime(long lastApiCallTime) {
        SessionManager.lastApiCallTime = lastApiCallTime;
    }

    public static String getSession() {
        return session;
    }

    public static long getLastApiCallTime() {
        return lastApiCallTime;
    }
}
