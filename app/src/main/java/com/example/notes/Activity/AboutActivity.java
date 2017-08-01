package com.example.notes.Activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.notes.Dialog.ChooseDialog;
import com.example.notes.Dialog.MyOnClickListener;
import com.example.notes.Util.ShareUtil;
import com.example.notes.View.MsgToast;
import com.example.ui.R;

/**
 * 关于界面
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

    /**
     * 初始化列表
     */
    private void init_table(){

        //share
        findViewById(R.id.t1_table).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //"我在使用SimpleNote你也来试试吧"
                ShareUtil.shareText(AboutActivity.this,
                        AboutActivity.this.getResources().getString(R.string.shareApp));
            }
        });

        //history
        findViewById(R.id.t2_table).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, UpdateActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

        //contact me
        findViewById(R.id.content_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseCopyContent();
            }
        });
        //contact me
        findViewById(R.id.info_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseCopyContent();
            }
        });
    }

    /**
     * 选择复制e-mail或者git-hub到剪贴板
     */
    private void chooseCopyContent(){

        //多选对话框
        final ChooseDialog dialog = new ChooseDialog(this);

        dialog.show();
        dialog.setTitle("请选择");
        dialog.setInfo("复制到剪贴板");


        dialog.setChoose1("e-mail");
        dialog.setListener_1(new MyOnClickListener() {
            @Override
            public void onClick() {
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                manager.setText("lemonreds@163.com");
                MsgToast.showToast(AboutActivity.this,"复制e-mail成功!");

            }
        });

        dialog.setChoose2("源码地址");
        dialog.setListener_2(new MyOnClickListener() {
            @Override
            public void onClick() {
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                manager.setText("https://github.com/Lemonreds/SimpleNote");

                MsgToast.showToast(AboutActivity.this,"复制源码地址成功!");
            }
        });


        dialog.setChoose3("取消");
    }

}
