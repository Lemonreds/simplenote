package com.example.notes.ui;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notes.Dialog.HeadDialog;
import com.example.notes.util.MsgToast;
import com.example.ui.R;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.richeditor.RichEditor;

public class SettingActivity extends BaseActivity  implements View.OnClickListener {


    RichEditor mEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mEditor = (RichEditor) findViewById(R.id.editor);

        ImageButton hide = (ImageButton)findViewById(R.id.action_hide);
        hide.setOnClickListener(this);


        ImageButton bold = (ImageButton)findViewById(R.id.action_bold);
        bold.setOnClickListener(this);

        ImageButton italic = (ImageButton)findViewById(R.id.action_italic);
        italic.setOnClickListener(this);

        ImageButton  underline= (ImageButton)findViewById(R.id.action_underline);
        underline.setOnClickListener(this);

        ImageButton  font= (ImageButton)findViewById(R.id.action_font);
        font.setOnClickListener(this);




        ImageButton checkbox = (ImageButton)findViewById(R.id.action_checkbox);
        checkbox.setOnClickListener(this);

        ImageButton menuList= (ImageButton)findViewById(R.id.action_menulist);
        menuList.setOnClickListener(this);

        ImageButton menuBullte= (ImageButton)findViewById(R.id.action_menubullte);
        menuBullte.setOnClickListener(this);


        ImageButton left= (ImageButton)findViewById(R.id.action_left);
        left.setOnClickListener(this);

        ImageButton center= (ImageButton)findViewById(R.id.action_center);
        center.setOnClickListener(this);
        ImageButton right= (ImageButton)findViewById(R.id.action_right);
        right.setOnClickListener(this);


     //  mEditor.setEditorHeight(200);//起始编辑设置高度
    //   mEditor.setEditorFontSize(22);//设置字体大小
       // mEditor.setEditorFontColor(Color.BLACK);//设置字体颜色

       // mEditor.insertImage(RealPath, "image");



/**
        final TextView follow = (TextView) findViewById(R.id.follow);


        mEditor. setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                follow.setText(Integer.toString(text.length()));
                // Do Something
               // MsgToast.showToast(SettingActivity.this,text);
            }
        });
 **/
    }


    @Override
    public void onClick(View v) {
        switch ( v.getId()){
            case R.id.action_hide:
              //  HorizontalScrollView view = (HorizontalScrollView)findViewById(R.id.editor_bottom);
               // view.setVisibility(View.GONE);
                break;
            case R.id.action_bold:
                mEditor.setBold();
                break;
            case R.id.action_italic:
                mEditor.setItalic();
                break;
            case R.id.action_underline:
                mEditor.setUnderline();
                break;
            case R.id.action_font:
                MsgToast.showToast(this, mEditor.getHtml());
                break;


            case R.id.action_checkbox:
                mEditor.insertTodo();
                break;
            case R.id.action_menulist:
                mEditor.setNumbers();
                break;
            case R.id.action_menubullte:
                mEditor.setBullets();
                break;
            case R.id.action_left:
                mEditor.setAlignLeft();
                break;
            case R.id.action_center:
                mEditor.setAlignCenter();
                break;
            case R.id.action_right:
                mEditor.setAlignRight();
                break;

        }
    }
}