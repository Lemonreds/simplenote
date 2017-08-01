package com.example.notes.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ui.R;

/**
 * 欢迎界面
 */

public class WelcomeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
    }


    /**
     * 初始化
     */
    private void init(){

        //当计时结束,跳转至主界面
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
                checkHavePassWord();
            }
        }, 2500);
    }

    /**
     * 检查是否已设置密码
     */
    private  void checkHavePassWord(){

        Intent intent1 = new Intent(WelcomeActivity.this, SecurityActivity.class);
        //MODEL_VERIFY 验证密码模式
        intent1.putExtra("model",SecurityActivity.MODEL_VERIFY);
        startActivity(intent1);
    }
}
