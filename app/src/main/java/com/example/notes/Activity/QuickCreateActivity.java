package com.example.notes.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notes.Manager.NoteManager;
import com.example.notes.model.Date;
import com.example.notes.model.Note;
import com.example.ui.R;

public class QuickCreateActivity extends AppCompatActivity {

    private TextView title;

    private EditText content;
    private String currentFolderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_create);

        currentFolderName = getIntent().getStringExtra("currentFolderName");

        content = (EditText)findViewById(R.id.content_quick);

        title = (TextView) findViewById(R.id.title_toolbar);
        title.setText("快速创建");

        init_Toolbar();
    }


    private  void init_Toolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.pic_deleteall);//设置取消图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });


        toolbar.inflateMenu(R.menu.menu_quick);//设置右上角的填充菜单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //done
                String title;
                Date date = new Date();
                if(content.getText().length()>=10)
                    title=content.getText().toString().substring(0,9);
                else {
                    title ="未命名备忘录";
                }

                Note create_note = new Note(title, date,
                      ""  , content.getText().toString(), currentFolderName);

                NoteManager noteManager = new NoteManager(QuickCreateActivity.this, currentFolderName);
                noteManager.add(create_note);

                finish();
                return false;
            }
        });

    }


}
