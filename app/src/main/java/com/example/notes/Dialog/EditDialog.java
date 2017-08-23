package com.example.notes.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notes.Model.Note;
import com.example.notes.View.MsgToast;
import com.example.ui.R;

/**
 * 编辑对话框
 */

public class EditDialog extends android.app.Dialog implements View.OnClickListener{

    private TextView title;
    private EditText info;
    private Button yes;
    private Button no;
    private Context mContext;
    private int level;


    private MyOnClickListener noListener;//取消按钮被点击了的监听器
    private MyOnClickListener yesListener;//确定按钮被点击了的监听器

    /**
     *
     * @param context
     */
    public EditDialog(Context context) {
        super(context, R.style.MyDialog);
        mContext=context;
    }

    /**
     * 初始化
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit);
        initView();
        initEvent();
    }

    /**
     * 初始化界面
     */
    protected void initView(){

        yes = (Button) findViewById(R.id.yes_dialog);
        no = (Button) findViewById(R.id.no_dialog);
        info = (EditText) findViewById(R.id.edit_dialog);
        title = (TextView) findViewById(R.id.title_dialog);

        findViewById(R.id.btn_red).setOnClickListener(this);
        findViewById(R.id.btn_green).setOnClickListener(this);
        findViewById(R.id.btn_orange).setOnClickListener(this);
    }


    /**
     * 注册监听事件
     */
    protected  void initEvent() {
        //确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesListener != null) {
                    yesListener.onClick();
                }
            }
        });
        //取消按钮被点击后，向外界提供监听
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noListener != null) {
                  noListener.onClick();
                }else{
                    dismiss();//若没有新设置事件,则默认关闭Dialog
                }
            }
        });

    }


    /**
     *
     * @param listener
     */
    public void setYesListener (MyOnClickListener listener){
        this.yesListener = listener;
    }

    /**
     *
     * @param listener
     */
    public void setNoListener (MyOnClickListener listener){
        this.noListener = listener;
    }


    /**
     *
     * @return
     */
    public String getInfo() {

        return info.getText().toString();
    }



    public void setTitle(String title) {
        this.title.setText(title);
    }


    public void setInfo(String msg){
        if(info!=null){
            info.setText(msg);
            info.setSelection(info.length());
        }
    }


    /**
     * 监听事件
     * @param v
     */
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

    /**
     * 改变level
     * @param v
     */

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
        MsgToast.showToast(mContext, sb.toString());
    }

    public int getLevel() {
        return level;
    }
}
