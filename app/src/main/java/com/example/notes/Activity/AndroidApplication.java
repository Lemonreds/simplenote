package com.example.notes.Activity;

import android.app.Application;

import com.example.ui.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by 阿买 on 2017/4/25.
 */

public class AndroidApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }



}
