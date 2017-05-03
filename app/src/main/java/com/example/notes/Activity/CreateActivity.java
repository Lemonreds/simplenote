package com.example.notes.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.notes.Dialog.InfoDialog;
import com.example.notes.Dialog.ProDialog;
import com.example.notes.Interface.MyOnClickListener;
import com.example.notes.util.LocationUtil;
import com.example.notes.Manager.NoteManager;
import com.example.notes.model.Date;
import com.example.notes.util.MsgToast;
import com.example.notes.model.Note;
import com.example.notes.util.StringUtil;
import com.example.ui.R;

import java.util.Timer;
import java.util.TimerTask;

import jp.wasabeef.richeditor.RichEditor;

public class CreateActivity extends BaseActivity implements View.OnClickListener {


    private TextView model_title;

    private EditText title;
    private RichEditor mEditor;
    private Date date;
    private TextView date_view;
    private TextView location;
    private int level;
    private String currentFolderName;


    private boolean model; //false 是新建模式    true是编辑模式
    private Note edit_Note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


        model_title = (TextView) findViewById(R.id.title_toolbar);


        Intent intent = this.getIntent();//新建模式
        currentFolderName = intent.getStringExtra("currentFolderName");
        model_title.setText("新备忘录");

        if(currentFolderName == null){//编辑模式
            model_title.setText("编辑备忘录");
            model = true;//更改状态
            edit_Note = (Note) intent.getSerializableExtra("note");//获取编辑的note
            currentFolderName = edit_Note.getFolderName();
        }


        init_view();
        init_Toolbar();
        init_NoteEditor();

        if(model){//如果是编辑模式
            init_edit();
        }
    }



    private void init_edit(){

        title.setText( edit_Note.getName() );
        mEditor.setHtml( edit_Note.getText() );
        date_view.setText( edit_Note.getDate().getDetailDate() );
        location.setText( edit_Note.getLocation());

    }
    private  void init_view(){

        title = (EditText) findViewById(R.id.title_create);
        location  = (TextView) findViewById(R.id.location_create);

        date_view = (TextView) findViewById(R.id.date_create);
        date = new Date( );
        date_view.setText(date.getDetailDate());



        Button btn_red = (Button) findViewById(R.id.btn_red);
        btn_red.setOnClickListener(this);
        Button btn_orange = (Button) findViewById(R.id.btn_orange);
        btn_orange.setOnClickListener(this);
        Button btn_green = (Button) findViewById(R.id.btn_green);
        btn_green.setOnClickListener(this);

        init_fab();
        init_bottom();
    }

    private void init_bottom(){

       findViewById(R.id.open_bottom_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.bottom_create).setVisibility(View.GONE);
                findViewById(R.id.editor_bottom).setVisibility(View.VISIBLE);
            }
        });

        ImageView  reBack = (ImageView)findViewById(R.id.reback_bottom_create);

        if(!model) {//编辑模式
            reBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!model) {//编辑模式
                        if (isEdit()) {
                            MsgToast.showToast(CreateActivity.this, "还没开始编辑呢");
                        } else {

                            final InfoDialog warnDialog = new InfoDialog(CreateActivity.this);
                            warnDialog.show();
                            warnDialog.setTitle("提示");
                            warnDialog.setEnableEdit(false);
                            warnDialog.setInfo("将所编辑内容全部清空吗？");
                            warnDialog.setYesListener(new MyOnClickListener() {
                                @Override
                                public void onClick() {
                                    warnDialog.dismiss();
                                    reset();
                                }
                            });

                        }
                    }
                }
            });
        }else{
            reBack.setVisibility(View.GONE);
        }

        findViewById(R.id.hide_bottom_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(R.id.fab_create).setVisibility(View.VISIBLE);
                findViewById(R.id.editor_bottom).setVisibility(View.GONE);
                findViewById(R.id.bottom_create).setVisibility(View.GONE);
            }
        });

        findViewById(R.id.location_bottom_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

    }

    private  boolean isEdit(){
        return StringUtil.isEmpty(title.getText().toString()) &&
                StringUtil.isEmpty(mEditor.getHtml());
    }

    private void reset(){

        title.setText("");
        mEditor.setHtml("");
        location.setText("未定位");
        level=Note.GRE_LEVEL;
    }

    private void init_fab(){

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_create);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.editor_bottom).setVisibility(View.VISIBLE);
                findViewById(R.id.bottom_create).setVisibility(View.VISIBLE);
                fab.setVisibility(View.GONE);
            }
        });

       // init_bottom();
    }



    private void hideOrOpenKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    private void getLocation(){


            final ProDialog proDialog = new ProDialog(this,"正在定位...");
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

            location.setText(address);
    }

    private  void init_NoteEditor() {



        mEditor = (RichEditor) findViewById(R.id.editor);
        mEditor.setFontSize(14);
        mEditor.setPlaceholder("在这里写下内容");


        findViewById(R.id.action_hide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.bottom_create).setVisibility(View.VISIBLE);
                findViewById(R.id.editor_bottom).setVisibility(View.GONE);
            }
        });


        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBold();
            }
        });


        findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setItalic();
            }
        });
        findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setUnderline();
            }
        });

        findViewById(R.id.action_deleteline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setStrikeThrough();
            }
        });



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


       findViewById(R.id.action_center).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mEditor.setAlignCenter();
           }
       });
        findViewById(R.id.action_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignRight();
            }
        });
    }





    private  void init_Toolbar(){

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.pic_deleteall);//设置取消图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });


        toolbar.inflateMenu(R.menu.menu_create);//设置右上角的填充菜单


        if(model) {//编辑模式
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    NoteManager noteManager = new NoteManager(CreateActivity.this,currentFolderName);

                    Note newNote = new Note(title.getText().toString(),  new Date(),
                            location.getText().toString(), mEditor.getHtml(),currentFolderName,level);

                    noteManager.update(edit_Note,newNote);
                    MsgToast.showToast(CreateActivity.this,"已保存");
                    finish();
                    return false;
                }
            });


        }else {//新建模式
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    Note create_note = new Note(title.getText().toString(), date,
                            location.getText().toString(), mEditor.getHtml(),
                            currentFolderName, level);

                    NoteManager noteManager = new NoteManager(CreateActivity.this, currentFolderName);
                    noteManager.add(create_note);
                    hideOrOpenKeyBoard();
                    finish();
                    return false;
                }
            });

        }


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

             case R.id.btn_red:
             case R.id.btn_green:
             case R.id.btn_orange:
             change_level(v);
             break;


          }
    }



    private void change_level(View v) {


        StringBuilder sb = new StringBuilder(4);
        switch (v.getId()) {
            case R.id.btn_red:
                level = Note.RED_LEVEL;
                sb.append("Red");
                break;
            case R.id.btn_orange:
                level = Note.ORA_LEVEL;
                sb.append("Orange");
                break;
            case R.id.btn_green:
                level = Note.GRE_LEVEL;
                sb.append("Green");
                break;
        }
        MsgToast.showToast(this, sb.toString());
    }
}
