package com.example.notes.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.notes.Manager.LocationManager;
import com.example.notes.Manager.NoteManager;

import com.example.notes.View.EditListener;
import com.example.notes.util.MsgToast;
import com.example.notes.util.Note;
import com.example.notes.Dialog.ProDialog;
import com.example.notes.util.StringUtil;
import com.example.ui.R;

import java.util.Timer;
import java.util.TimerTask;

public class ContentActivity extends BaseActivity implements View.OnClickListener{



    private TextView mTitle;

    private EditText content;

    private Note note;

    private String currentFolderName;

    private Toolbar mToolbar;

    private boolean edit; //用于保存右上角 编辑（false）或完成(true) 的状态


    private NoteManager mNoteManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        init();
    }



    private void editOrSave(){

        if(edit){//如果是编辑状态
            editContent();
        }else{//保存状态
            saveContent(true);
        }
    }

    private  void editContent(){
        content.setFocusableInTouchMode(true);
        content.setFocusable(true);
        content.requestFocus();
        hideOrOpenKeyBoard();
    }

    private void saveContent(boolean save){

        if(save) {
            NoteManager mNoteManager = new NoteManager(this, currentFolderName);
            note = mNoteManager.updateContent(note, content.getText().toString());
        }

        content.setFocusable(false);
        content.setFocusableInTouchMode(false);
        hideOrOpenKeyBoard();

    }


    private void hideOrOpenKeyBoard(){

        openBottom(true);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    private void openBottom(boolean b){

        LinearLayout bottom =(LinearLayout) findViewById(R.id.bottom_content);
        if(b){
            bottom.setVisibility(View.VISIBLE);
        }
        else{
            bottom.setVisibility(View.GONE);
        }
    }

    //reBack按钮监听
    private void  reBack(){

        content.setText(note.getText());
        content.setSelection(note.getText().length());

        //更改编辑状态
        edit=!edit;

        saveContent(false);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_red:
            case R.id.btn_green:
            case R.id.btn_orange:
                change_level(v);
                break;

            //case R.id.hide_bottom_content:
            //    openBottom(false);
              //  break;
            case R.id.delete_bottom_content:
                deleteNote();
                break;
            case R.id.location_bottom_content:
                getLocation();
                break;


            default:
                break;
        }

    }

    private void change_level(View v){

        int level = note.getLevel();

        switch (v.getId()){
            case R.id.btn_red:
                level = Note.RED_LEVEL;
                break;
            case R.id.btn_orange:
                level = Note.ORA_LEVEL;
                break;
            case R.id.btn_green:
                level = Note.GRE_LEVEL;
                break;
        }


        mNoteManager = new NoteManager(this, currentFolderName);

        note = mNoteManager.updateLevel(note,level);

        MsgToast.showToast(this,"已成功修改");
    }


    private void getLocation(){

        final  ProDialog proDialog = new ProDialog(this,"正在获取定位...");
        proDialog.show();

        LocationManager mLocationMag = new LocationManager(getApplicationContext());

        final TextView location  = (TextView) findViewById(R.id.locate_content);
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



        mNoteManager = new NoteManager(this, currentFolderName);

        note = mNoteManager.updateLocation(note,address);

        location.setText(address);

    }


    //获取item并打开响应的activity
    private void init(){


        init_toolbar();
        init_bottom();

        currentFolderName = getIntent().getStringExtra("currentFolderName");


        Intent intent = this.getIntent();
        note = (Note) intent.getSerializableExtra("note");


        TextView date = (TextView) findViewById(R.id.date_content);
        TextView location  = (TextView) findViewById(R.id.locate_content);

        if(StringUtil.isEmpty(note.getLocation()))
            location.setVisibility(View.GONE);
        else {
            location.setText(note.getLocation());
        }

        content = (EditText)findViewById(R.id.edit_content);
        content.setFocusable(false);


        mTitle.setText(note.getName());
        date.setText(note.getDate().getDetailDate());
        content.setText(note.getText());
        content.setSelection(content.getText().length());

        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAction();
                content.setSelection(content.getText().length());
            }
        });
        TextView numberFollow = (TextView)findViewById(R.id.number_bottom_content);
        content.addTextChangedListener(new EditListener(content,numberFollow));
    }




    private void init_toolbar() {

       mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.title_toolbar);

        mToolbar.inflateMenu(R.menu.menu_content);


        mToolbar.getMenu().getItem(0).setVisible(false);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.edit_content_menu:
                        editAction();
                        break;
                    case R.id.reBack_content_menu:
                        reBack();
                        mToolbar.getMenu().getItem(0).setVisible(false);
                        mToolbar.getMenu().getItem(1).setIcon(R.drawable.pic_edit);
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


    }



    private void editAction(){
        edit=!edit;
        editOrSave();

        if(!edit) {//如果是保存
            mToolbar.getMenu().getItem(0).setVisible(false);
            mToolbar.getMenu().getItem(1).setIcon(R.drawable.pic_edit);
        }else{
            mToolbar.getMenu().getItem(0).setVisible(true);
            mToolbar.getMenu().getItem(1).setIcon(R.drawable.pic_done);
        }
    }



    private  void init_bottom(){

       // ImageView hide = (ImageView) findViewById(R.id.hide_bottom_content);
       // hide.setOnClickListener(this);
        ImageView delete = (ImageView) findViewById(R.id.delete_bottom_content);
        delete.setOnClickListener(this);
        ImageView location = (ImageView) findViewById(R.id.location_bottom_content);
        location.setOnClickListener(this);



        Button btn_red = (Button) findViewById(R.id.btn_red);
        btn_red.setOnClickListener(this);
        Button btn_orange = (Button) findViewById(R.id.btn_orange);
        btn_orange.setOnClickListener(this);
        Button btn_green = (Button) findViewById(R.id.btn_green);
        btn_green.setOnClickListener(this);
    }



    private void deleteNote(){

        NoteManager noteManager = new NoteManager(this,currentFolderName);
        noteManager.deleteNote(note);
    }






}
