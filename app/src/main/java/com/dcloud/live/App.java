package com.dcloud.live;

import android.app.Application;

/**
 * Created by wubo on 2018/3/27.
 */

public class App extends Application {
    private static App instance;
    //加密证书
    public static final String PUBLIC_FILE_NAME = "haixiang.cer";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }
}
