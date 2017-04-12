package com.example.notes.util;

/**
 * Created by 阿买 on 2017/1/19.
 */

public class StringUtil {

    public static boolean isEmpty(String str){
        if(str==null || str.trim().equals("") )
            return true;
        else return false;

    }
}
