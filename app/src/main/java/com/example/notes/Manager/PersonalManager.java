package com.example.notes.Manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.example.notes.Util.AppUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by 阿买 on 2017/4/12.
 */

public class PersonalManager {


    private Context mContent;

    private static String path = "/sdcard/myHead/";// sd路径

    public PersonalManager(Context mContent) {
        this.mContent = mContent;
    }


    public Drawable getHeadImg(){

        if(AppUtil.isFirstStart((Activity) mContent)){
            return  null;
        }

        Bitmap bt = getBitmap(path + "head.jpg");
        if(bt==null) return null;
        Drawable drawable = new BitmapDrawable(bt);
        return drawable;
    }

    public void setHeadImg(Uri uri){
        Bitmap head;
          try {
              head = MediaStore.Images.Media.getBitmap
                      (mContent.getContentResolver(), uri);

              if (head != null) {
                  saveIgmToSD(head);// 保存在SD卡中
                  if (head != null && head.isRecycled()) {
                      head.recycle();
                  }
              }
          }
        catch (Exception e){
        }
    }


    private void saveIgmToSD(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    // 从本地的文件中以保存的图片中 获取图片的方法
    private Bitmap getBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
 }







    public void savePersonName(String name) {

        SharedPreferences.Editor editor = mContent.getSharedPreferences("personal_Name", Context.MODE_PRIVATE).edit();

        editor.putString("name", name);
        editor.apply();
    }

    public String getPersonName() {
        SharedPreferences reader = mContent.getSharedPreferences("personal_Name", Context.MODE_PRIVATE);

        String name = reader.getString("name", "SimpleNote");
        return name;
    }



}



