package com.example.notes.Activity;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notes.Dialog.ProDialog;
import com.example.notes.Manager.NoteManager;
import com.example.notes.Manager.PersonalManager;
import com.example.notes.Model.Date;
import com.example.notes.Model.Note;
import com.example.notes.Util.LocationUtil;
import com.example.ui.R;

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuickCreateActivity extends AppCompatActivity {


    private EditText content;
    private String location="";
    private String currentFolderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_create);

        currentFolderName = getIntent().getStringExtra("currentFolderName");
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
                if(content.getText().length()>=20)
                    title=content.getText().toString().substring(0,19);
                else {
                    title ="未命名备忘录";
                }


                Note create_note = new Note(title, date,
                        location  , content.getText().toString(), currentFolderName);

                NoteManager noteManager = new NoteManager(QuickCreateActivity.this, currentFolderName);
                noteManager.add(create_note);

                finish();
                return false;
            }
        });

        //init head
        CircleImageView head = (CircleImageView)findViewById(R.id.head_quick);
        Drawable useHead = new PersonalManager(this).getHeadImg();
        if(useHead!=null){
            head.setImageDrawable(useHead);
        }
        //init bottom
        final TextView locationButton =(TextView)findViewById(R.id.location_bottom_quick);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = getLocation();
                locationButton.setText(location);
            }
        });



        final TextView word =(TextView)findViewById(R.id.words_bottom_quick);


        content = (EditText)findViewById(R.id.content_quick);


        content.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               //实时记录输入的字数
                word.setText(" " + s.length()+" ");
            }

            @Override
            public void afterTextChanged(Editable s) {
                for(int i = s.length(); i > 0; i--){
                }
            }
        });



    }



    /**
     * 获取位置响应
     */
    private String getLocation() {

        final ProDialog proDialog = new ProDialog(this, "正在获取定位...");
        proDialog.show();

        LocationUtil mLocationMag = new LocationUtil(getApplicationContext());

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                proDialog.dismiss();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1500);

        String address = mLocationMag.getLocation();

        return address;
    }


}
