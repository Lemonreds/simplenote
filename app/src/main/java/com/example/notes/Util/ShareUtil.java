package com.example.notes.Util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.view.View;

import com.example.ui.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 分享接口
 */

public class ShareUtil {


    /**
     * 分享图片
     * @param activity
     */
    public  static  void shareImg(Activity activity){


        String image = shoot(activity);
        Intent intent  = new Intent(Intent.ACTION_SEND);
        File file = new File(image);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        intent.setType("image/jpeg");
        //"将内容截图分享给...."
        Intent chooser = Intent.createChooser(intent,
                activity.getResources().getString(R.string.shareImg));
        if(intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(chooser);
        }
    }


    /**
     * 分享文字
     * @param activity
     * @param text
     */
    public static  void shareText(Activity activity,String text){

        Intent intent=new Intent(Intent.ACTION_SEND);

        intent.setType("text/plain");
        //"分享"
        intent.putExtra(Intent.EXTRA_SUBJECT, activity.getResources().getString(R.string.share));
        //"\n分享自SimpleNote"
        intent.putExtra(Intent.EXTRA_TEXT,text+"\n"+activity.getResources().getString(R.string.shareFrom));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //"分享你的内容到...."
        activity.startActivity(Intent.createChooser(intent,activity.getResources().getString(R.string.shareTo)));

    }

    /**
     * 分享截图
     * @param a
     * @return
     */
    public static String shoot(Activity a) {
        String strFileName = "sdcard/" + String.valueOf(System.currentTimeMillis()) + ".png";
        ShareUtil.savePic(ShareUtil.takeScreenShot(a), strFileName);
        return strFileName;
    }

    /**
     * 截图
     * @param activity
     * @return
     */
    private static Bitmap takeScreenShot(Activity activity) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();
        // 去掉标题栏
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

    /**
     * 保存到SD卡
     * @param b
     * @param strFileName
     */
    private static void savePic(Bitmap b, String strFileName) {

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(strFileName);
            b.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
