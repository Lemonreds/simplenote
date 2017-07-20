package com.example.notes.Util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

public class AppUtil {


    public static boolean isFirstStart(Activity activity){
       // 定义一个setting记录APP是几次启动
       SharedPreferences setting =activity.getSharedPreferences("FirstStart", Context.MODE_PRIVATE);
        Boolean user_first = setting.getBoolean("FIRST", true);
       if (user_first) {// 第一次
          setting.edit().putBoolean("FIRST", false).apply();
         return true;
       }
       else return false;
    }

    public static boolean haveDescription(Activity activity){
        // 定义一个setting记录APP是几次启动
        SharedPreferences setting =activity.getSharedPreferences("Description", Context.MODE_PRIVATE);
        Boolean user_first = setting.getBoolean("FIRST", true);
        if (user_first) {// 第一次
            setting.edit().putBoolean("FIRST", false).apply();
            return true;
        }
        else return false;
    }

}
