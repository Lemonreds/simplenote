package com.example.notes.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 阿买 on 2017/4/12.
 */

public class PersonalInfoUtil {


    private Context mContent;




    public PersonalInfoUtil(Context mContent) {
        this.mContent = mContent;
    }


    public void savePersonName(String name){

        SharedPreferences.Editor editor = mContent.getSharedPreferences("personal_Name",Context.MODE_PRIVATE).edit();

        editor.putString("name",name);
        editor.apply();
    }

    public String getPersonName(){
        SharedPreferences reader = mContent.getSharedPreferences("personal_Name", Context.MODE_PRIVATE);

        String name = reader.getString("name", "SimpleNote");
        return name;
    }

}
