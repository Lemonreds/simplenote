package com.example.notes.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.example.notes.util.LocationUtil;
import com.example.notes.Manager.NoteManager;
import com.example.notes.model.Note;
import com.example.notes.Dialog.ProDialog;
import com.example.notes.util.MsgToast;
import com.example.notes.util.StringUtil;
import com.example.ui.R;
import java.util.Timer;
import java.util.TimerTask;


public class ContentActivity extends BaseActivity  {


    private TextView mTitle;

    private TextView content;

    private Note note;

    private String currentFolderName;

    private Toolbar mToolbar;


    private NoteManager mNoteManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        //获取mainActivity传来的信息
        Intent intent = this.getIntent();
        currentFolderName = intent.getStringExtra("currentFolderName");
        note = (Note) intent.getSerializableExtra("note");
        mNoteManager = new NoteManager(this,currentFolderName);
        init();
    }


    //获取item并打开响应的activity
    private void init() {

        init_toolbar();
        init_view();
        init_bottom();

    }




    private void init_toolbar() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.menu_content);


        mToolbar.setNavigationIcon(R.drawable.pic_back);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                          Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intent.putExtra(Intent.EXTRA_TEXT,content.getText().toString()+ " " +"分享自SimpleNote");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, "从SimpleNote分享内容到...."));
                return false;
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });


        //toolbar上的标题
        mTitle = (TextView) findViewById(R.id.title_toolbar);
       // setEditTextEditable(mTitle, false);
        mTitle.setText(note.getName());
       // mTitle.setSelection(mTitle.getText().length());

    }


    private void init_view(){
        //设置noteMng
        mNoteManager = new NoteManager(this, currentFolderName);

        //日期
        final TextView date = (TextView) findViewById(R.id.date_content);
        date.setText(note.getDate().getDetailDate());

        //位置
        final TextView location = (TextView) findViewById(R.id.location_content);
        //位置信息的判断
        if (StringUtil.isEmpty(note.getLocation()))
            location.setVisibility(View.GONE);
        else {
            location.setText(note.getLocation());
        }

        content =(TextView)findViewById(R.id.content);
        content.setText(Html.fromHtml(note.getText()));

        TextView numberFollow = (TextView)findViewById(R.id.numberFollow_content);
        numberFollow.setText(" "+StringUtil.clearHtml(content.getText().toString()).length()+" ");


        switch (note.getLevel()){
            case Note.GRE_LEVEL:
                findViewById(R.id.level_content).setBackgroundResource(R.drawable.radius_green);
                break;
            case Note.ORA_LEVEL:
                findViewById(R.id.level_content).setBackgroundResource(R.drawable.radius_orange);
                break;
            case Note.RED_LEVEL:
                findViewById(R.id.level_content).setBackgroundResource(R.drawable.radius_red);
                break;
        }

    }

    private void init_bottom() {


        findViewById(R.id.edit_bottom_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                edit();
            }
        });


        findViewById(R.id.delete_bottom_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNoteManager.deleteNote(note);
                MsgToast.showToast(ContentActivity.this,"已移至'最近删除'");
                finish();
            }
        });

        findViewById(R.id.move_bottom_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Intent intent = new Intent(ContentActivity.this,FilesActivity.class);
                intent.putExtra("move",true);

                Bundle bundle = new Bundle();
                bundle.putSerializable("note",note);
                intent.putExtras(bundle);
                startActivity(intent);

                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                //move

            }
        });


        findViewById(R.id.location_bottom_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });




    }


    private  void edit(){

        Intent intent = new Intent(this,CreateActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("note", note);
        intent.putExtras(bundle);

        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        finish();
    }


    private void getLocation() {

        final ProDialog proDialog = new ProDialog(this, "正在获取定位...");
        proDialog.show();

        LocationUtil mLocationMag = new LocationUtil(getApplicationContext());

        final TextView location = (TextView) findViewById(R.id.location_content);
        location.setVisibility(View.VISIBLE);


        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                proDialog.dismiss();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1500);

        String address = mLocationMag.getLocation();

        note = mNoteManager.updateLocation(note, address);

        location.setText(address);
    }



}
