package com.example.notes.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.notes.util.MsgToast;
import com.example.ui.R;

public class WelcomeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        checkStart();

    }
    private void checkStart(){
        //定义一个setting记录APP是几次启动！！！
        SharedPreferences setting = getSharedPreferences("FirstStart", Context.MODE_PRIVATE);
        Boolean user_first = setting.getBoolean("FIRST", true);

        if (user_first) {// 第一次
            setting.edit().putBoolean("FIRST", false).apply();
            firstStart();
        } else {
            noFirstStart();
        }
    }

    private void firstStart() {

        Button startUse = (Button)findViewById(R.id.firstButton);
        startUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgToast.showToast(WelcomeActivity.this,"谢谢你的使用");
                WelcomeActivity.this.finish();
                checkHavePassWord();
            }
        });

    }

    private  void noFirstStart(){
        findViewById(R.id.firstButton).setVisibility(View.GONE);
        Handler handler = new Handler();
        //当计时结束,跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
                checkHavePassWord();
            }
        }, 3000);
    }


    private  void checkHavePassWord(){
        Intent intent1 = new Intent(WelcomeActivity.this, SecurityActivity.class);
        intent1.putExtra("model",SecurityActivity.MODLE_VERIFY);
        startActivity(intent1);
    }

}
