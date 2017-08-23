package com.example.notes.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.notes.Util.StringUtil;
import com.example.ui.R;

/**
 * 多选对话框
 */

public class ChooseDialog extends Dialog {


    private TextView title;
    private TextView info;
    private TextView choose1;
    private TextView choose2;
    private TextView choose3;


    private MyOnClickListener listener_1;
    private MyOnClickListener listener_2;
    private MyOnClickListener listener_3;



    public ChooseDialog(Context context) {
        super(context, R.style.MyDialog);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView(){

        title = (TextView) findViewById(R.id.title_dialog);
        info =(TextView) findViewById(R.id.info_dialog);
        choose1= (TextView)findViewById(R.id.choose1_dialog);
        choose2= (TextView)findViewById(R.id.choose2_dialog);
        choose3= (TextView)findViewById(R.id.choose3_dialog);

        choose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener_1 != null) {
                    listener_1.onClick();
                }
                dismiss();//若没有新设置事件,则默认关闭Dialog

            }
        });

        choose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener_2 != null) {
                    listener_2.onClick();
                }
                dismiss();//若没有新设置事件,则默认关闭Dialog

            }
        });

        choose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener_3 != null) {
                    listener_3.onClick();
                }
                dismiss();//若没有新设置事件,则默认关闭Dialog

            }
        });

    }


    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setInfo(String info) {
        this.info.setText(info);
    }

    public void setChoose1(String choose1) {
        if(StringUtil.isEmpty(choose1))this.choose1.setVisibility(View.GONE);
        this.choose1.setText(choose1);
    }

    public void setChoose2(String choose2) {
        if(StringUtil.isEmpty(choose2))this.choose2.setVisibility(View.GONE);
        this.choose2.setText(choose2);
    }

    public void setChoose3(String choose3) {
        if(StringUtil.isEmpty(choose3))this.choose3.setVisibility(View.GONE);
        this.choose3.setText(choose3);
    }


    public void setListener_1(MyOnClickListener listener_1) {
        this.listener_1 = listener_1;
    }

    public void setListener_2(MyOnClickListener listener_2) {
        this.listener_2 = listener_2;
    }




}
