package com.example.notes.Util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by 阿买 on 2017/5/12.
 */

public class IntentUtil {


    public  static  void startShootShareIntent(Activity activity){


        String image = ShareUtil.shoot(activity);
        Intent intent  = new Intent(Intent.ACTION_SEND);
        File file = new File(image);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        intent.setType("image/jpeg");
        Intent chooser = Intent.createChooser(intent, "\n分享自SimpleNote");
        if(intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(chooser);
        }
    }

    public static  void startWordShareIntent(Activity activity,String text){

         Intent intent=new Intent(Intent.ACTION_SEND);

         intent.setType("text/plain");
         intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
         intent.putExtra(Intent.EXTRA_TEXT,text+ " " +"\n分享自SimpleNote");
         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         activity.startActivity(Intent.createChooser(intent, "分享你的内容到...."));

    }
}
