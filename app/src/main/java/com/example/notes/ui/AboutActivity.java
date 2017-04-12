package com.example.notes.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.ui.R;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        init_toolbar();
    }


    private void init_toolbar(){
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        TextView mTitle = (TextView) findViewById(R.id.title_toolbar);
        mTitle.setText("关于");
        //mToolbar.inflateMenu(R.menu.menu_content);

        mToolbar.setNavigationIcon(R.drawable.pic_back);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });
    }

}
