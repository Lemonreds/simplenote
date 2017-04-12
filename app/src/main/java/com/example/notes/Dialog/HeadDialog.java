package com.example.notes.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notes.Interface.onNoOnclickListener;
import com.example.notes.Interface.onYesOnclickListener;
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

    private onNoOnclickListener noListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesListener;//确定按钮被点击了的监听器

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
                    yesListener.onYesClick();
                }
            }
        });
        //取消按钮被点击后，向外界提供监听
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noListener != null) {
                    noListener.onNoClick();
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


    /**
     *
     * @param listener
     */
    public void setYesListener (onYesOnclickListener listener){
        this.yesListener = listener;
    }

    /**
     *
     * @param listener
     */
    public void setNoListener (onNoOnclickListener listener){
        this.noListener = listener;
    }
}
