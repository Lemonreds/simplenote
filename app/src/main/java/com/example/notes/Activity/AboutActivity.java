package com.example.notes.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.notes.Util.MsgToast;
import com.example.ui.R;

/**
 * 关于
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        init_toolbar();

        init_table();
    }

    /**
     * toolbar初始化
     */
    private void init_toolbar(){
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);


        mToolbar.setNavigationIcon(R.drawable.pic_back);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });
    }

    private void init_table(){

        //share
        findViewById(R.id.t1_table).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intent.putExtra(Intent.EXTRA_TEXT,"我在使用SimpleNote你也来试试吧");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, "把 SimpleNote 分享给...."));
            }
        });



        findViewById(R.id.t2_table).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgToast.showToast(AboutActivity.this,"现在还不能反馈");
            }
        });



        findViewById(R.id.t3_table).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgToast.showToast(AboutActivity.this,"这个好像也没有");
            }
        });

    }

}
