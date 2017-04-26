package com.example.notes.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.example.notes.Manager.LocationManager;
import com.example.notes.Manager.NoteManager;
import com.example.notes.util.Note;
import com.example.notes.Dialog.ProDialog;
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

    //private ImageView done;

    //private boolean edit;

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


        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.share_content_menu:
                       // editAction();
                        break;
                }
                return false;
            }
        });


        mToolbar.setNavigationIcon(R.drawable.pic_back);


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
       /** mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditTextEditable(mTitle, true);
                done.setVisibility(View.VISIBLE);
            }
        });

**/

/**
        //toolbar上标题编辑是否完成按钮
        done = (ImageView) findViewById(R.id.done_toolbar);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //savatitle
                mNoteManager.update(note, mTitle.getText().toString());
                MsgToast.showToast(ContentActivity.this, "完成");
                showToolbarMenu(true);
                done.setVisibility(View.GONE);
                setEditTextEditable(mTitle, false);
            }
        });

    }
 **/
    }


/**

  private void init_NoteEditor(){

        mEditor = (RichEditor) findViewById(R.id.editor);
        mEditor.setFontSize(14);



       findViewById(R.id.editor_bottom).setVisibility(View.GONE);

        findViewById(R.id.action_hide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.bottom).setVisibility(View.VISIBLE);
                findViewById(R.id.editor_bottom).setVisibility(View.GONE);
            }
        });


        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBold();
            }
        });
        // bold.setOnClickListener(this);

        findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setItalic();
            }
        });
        //italic.setOnClickListener(this);

        findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setUnderline();
            }
        });
        //underline.setOnClickListener(this);

        findViewById(R.id.action_font).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //ont.setOnClickListener(this);


        // photo.setOnClickListener(this);

        findViewById(R.id.action_checkbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertTodo();
            }
        });

        findViewById(R.id.action_menulist).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mEditor.setNumbers();
            }
        });
        //menuList.setOnClickListener(this);

        findViewById(R.id.action_menubullte).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBullets();
            }
        });

        findViewById(R.id.action_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignLeft();
            }
        });


        f
 **/
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



/*
        Button btn_red = (Button) findViewById(R.id.btn_red);
        btn_red.setOnClickListener(this);
        Button btn_orange = (Button) findViewById(R.id.btn_orange);
        btn_orange.setOnClickListener(this);
        Button btn_green = (Button) findViewById(R.id.btn_green);
        btn_green.setOnClickListener(this);
        **/
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

        LocationManager mLocationMag = new LocationManager(getApplicationContext());

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
