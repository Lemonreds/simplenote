package com.example.notes.Util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 阿买 on 2017/5/4.
 */

public class AppUtil {


    public static boolean isFirstStart(Activity activity){
        //定义一个setting记录APP是几次启动
        SharedPreferences setting =activity.getSharedPreferences("FirstStart", Context.MODE_PRIVATE);
        Boolean user_first = setting.getBoolean("FIRST", true);

        if (user_first) {// 第一次
            setting.edit().putBoolean("FIRST", false).apply();
            return true;
        } else return false;
    }
}
