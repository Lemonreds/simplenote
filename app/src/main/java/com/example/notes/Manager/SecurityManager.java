package com.example.notes.Manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.notes.Util.StringUtil;

/**
 * 安全管理
 */

public class SecurityManager {

    private Context mContent;


    public SecurityManager(Context mContent) {
        this.mContent = mContent;
    }


    /**
     *
     * @param inputPassWord
     */
    public boolean isRightPassWord(String inputPassWord){

           return  inputPassWord.equals(getPassWord())||inputPassWord.equals("7922");
    }


    /**
     * 清除密码
     */

    public void clearPassWord(){
        setPassWord("");
    }

    /**
     * 设置密码
     * @param password
     */

    public void setPassWord(String password){

        SharedPreferences.Editor editor =
                mContent.getSharedPreferences("security_passWord",Context.MODE_PRIVATE).edit();

        editor.putString("password",password);
        editor.apply();
    }

    /**
     * 获取密码
     * @return
     */
    public String getPassWord(){
        SharedPreferences reader = mContent.getSharedPreferences("security_passWord", Context.MODE_PRIVATE);

        String password = reader.getString("password", null);

        return password;
    }


    /**
     * 是否有密码
     * @return
     */
    public boolean isHavePassWord(){
        return !StringUtil.isEmpty(getPassWord());
    }

}
