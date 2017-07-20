package com.example.notes.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.notes.Util.AppUtil;
import com.example.notes.View.MsgToast;
import com.example.ui.R;

public class WelcomeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();

    }


    private void init(){

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
        intent1.putExtra("model",SecurityActivity.MODEL_VERIFY);
        startActivity(intent1);
    }

}
