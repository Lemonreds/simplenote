package com.example.notes.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notes.Interface.MyOnClickListener;
import com.example.ui.R;

/**
 * Created by 阿买 on 2017/4/8.
 */

public class HeadDialog extends  android.app.Dialog {


    private TextView title;
    private EditText info;

    private Button yes;
    private Button no;

    private Context mContext;

    private MyOnClickListener noListener;//取消按钮被点击了的监听器
    private MyOnClickListener yesListener;//确定按钮被点击了的监听器

    /**
     *
     * @param context
     */
    public HeadDialog(Context context) {
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
        setContentView(R.layout.dialog_head);
        setCanceledOnTouchOutside(false);

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
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                info.setFocusableInTouchMode(true);
                info.setFocusable(true);
                info.requestFocus();
                hideOrOpenKeyBoard();
            }
        });
        title.setText("个人信息");
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

    public String getPersonalName(){
        return info.getText().toString();
    }

    public void setPersonalName(String name) {
        info.setText(name);
        info.setSelection(name.length());
        info.setFocusable(false);
        info.setFocusableInTouchMode(false);
    }


    public void setYesListener(MyOnClickListener yesListener) {
        this.yesListener = yesListener;
    }

    public void setNoListener(MyOnClickListener noListener) {
        this.noListener = noListener;
    }

    private void hideOrOpenKeyBoard(){


        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
