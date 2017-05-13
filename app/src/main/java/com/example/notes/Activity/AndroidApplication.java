package com.example.notes.Activity;

import android.app.Application;

import com.example.ui.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created on 2017/4/25.
 * Application类
 */

public class AndroidApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        //用于全局字体设置
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Rohoto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }



}
