package com.carpediem.vv.funny.Utils;

import android.app.Application;

/**
 * Created by Administrator on 2016/6/13.
 */
public class MyApplication extends Application {
    private static MyApplication myApplication = null;
    public static Boolean firstStart=true;
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
    }
    public static MyApplication getInstance(){
        return myApplication;

    }
}
