package com.example.zujianhuaapp.base;
/*
 *  包名: com.example.zujianhuaapp.base
 * Created by ASUS on 2018/6/14.
 *  描述: TODO
 */

import android.app.Application;

import com.mob.MobSDK;


import cn.jpush.android.api.JPushInterface;

public class BaseApplication extends Application{

    private static BaseApplication baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication=this;
        MobSDK.init(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
/*
        UMConfigure.init(this,UMConfigure.DEVICE_TYPE_PHONE, null);*/

    }

    public static BaseApplication getInstance(){
        return baseApplication;
    }
}
