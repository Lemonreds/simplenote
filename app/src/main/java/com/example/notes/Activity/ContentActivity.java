package com.example.notes.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.notes.Dialog.ChooseDialog;
import com.example.notes.Dialog.MyOnClickListener;
import com.example.notes.Util.IntentUtil;
import com.example.notes.Util.LocationUtil;
import com.example.notes.Manager.NoteManager;
import com.example.notes.Model.Note;
import com.example.notes.Dialog.ProDialog;
import com.example.notes.Util.MsgToast;
import com.example.notes.Util.StringUtil;
import com.example.ui.R;

import java.util.Timer;
import java.util.TimerTask;

import jp.wasabeef.richeditor.RichEditor;

/**
 * 内容
 * 由Main触发的活动
 */
public class ContentActivity extends BaseActivity  {

    //展示的Note类
    private Note note;
    //Note管理类
    private NoteManager mNoteManager;


    //private String currentFolderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        //获取mainActivity传来的信息
        Intent intent = this.getIntent();
        note = (Note) intent.getSerializableExtra("note");
        init();
    }


    /**
     * ContentActivity初始化
     */
    private void init() {
        init_toolbar();
        init_view();
        init_bottom();
    }


    /**
     * toolbar的初始化
     */
    private void init_toolbar() {

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.menu_content);

        mToolbar.setNavigationIcon(R.drawable.pic_back);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {



                final ChooseDialog dialog = new ChooseDialog(ContentActivity.this);

                dialog.show();
                dialog.setTitle("请选择");
                dialog.setInfo("你想怎样分享你的备忘录");

                dialog.setChoose1("截图分享");
                dialog.setListener_1(new MyOnClickListener() {
                    @Override
                    public void onClick() {
                        IntentUtil.startShootShareIntent(ContentActivity.this);
                    }
                });

                dialog.setChoose2("仅文字分享");
                dialog.setListener_2(new MyOnClickListener() {
                    @Override
                    public void onClick() {
                        IntentUtil.startWordShareIntent
                             (ContentActivity.this,StringUtil.clearHtml(note.getText()));

                    }
                });
                dialog.setChoose3("取消");


               // IntentUtil.startShootShareIntent(ContentActivity.this);

               // IntentUtil.startWordShareIntent
               //         (ContentActivity.this,StringUtil.clearHtml(note.getText()));
                /**
                    Intent intent=new Intent(Intent.ACTION_SEND);

                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intent.putExtra(Intent.EXTRA_TEXT,note.getText()+ " " +"\n分享自SimpleNote");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, "分享你的内容到...."));
                 **/
                return false;
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_left);
            }
        });


        //toolbar上的标题
        TextView   mTitle = (TextView) findViewById(R.id.title_toolbar);
        mTitle.setText(note.getName());

    }





    /**
     * view的初始化
     */
    private void init_view(){
        //Note管理类
        mNoteManager = new NoteManager(this, note.getFolderName());

        //日期
        final TextView date = (TextView) findViewById(R.id.date_content);
        date.setText(note.getDate().getDetailDate());

        //位置
        final TextView location = (TextView) findViewById(R.id.location_content);

        if (StringUtil.isEmpty(note.getLocation()))
            location.setVisibility(View.GONE);
        else {
            location.setText(note.getLocation());
        }
        //内容
      //  TextView content =(TextView)findViewById(R.id.content);
       // content.setText(Html.fromHtml(note.getText()));

        RichEditor content = (RichEditor)findViewById(R.id.editor);
        content.setHtml(note.getText());
        content.setInputEnabled(false);


        //内容的长度
        //TextView numberFollow = (TextView)findViewById(R.id.numberFollow_content);
       // numberFollow.setText(" "+StringUtil.clearHtml(content.getText().toString()).length()+" ");
        TextView numberFollow = (TextView)findViewById(R.id.numberFollow_content);
         numberFollow.setText(" "+StringUtil.clearHtml(content.getHtml()).length()+" ");

        //Level
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

    /**
     * 底部栏的初始化
     */

    private void init_bottom() {

        //编辑
        findViewById(R.id.edit_bottom_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });
        //删除
        findViewById(R.id.delete_bottom_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNoteManager.deleteNote(note);
                MsgToast.showToast(ContentActivity.this,"已移至'最近删除'");
                finish();
            }
        });
        //移动到
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

            }
        });
        //位置
        findViewById(R.id.location_bottom_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });


    }

    /**
     * 编辑响应
     */
    private  void edit(){

        Intent intent = new Intent(this,CreateActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("note", note);
        intent.putExtras(bundle);

        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        finish();
    }

    /**
     * 获取位置响应
     */
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
