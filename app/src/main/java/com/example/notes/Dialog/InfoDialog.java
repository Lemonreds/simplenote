package com.example.notes.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ui.R;

/**
 * 提示框
 */

public class InfoDialog extends android.app.Dialog {

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
    public InfoDialog(Context context) {
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
        setContentView(R.layout.dialog_info);
       // setCanceledOnTouchOutside(false);
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



       /** info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info.setFocusableInTouchMode(true);
                info.setFocusable(true);
                info.requestFocus();
              //  hideOrOpenKeyBoard();
            }
        });
        **/
        title = (TextView) findViewById(R.id.title_dialog);
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

    private void hideOrOpenKeyBoard(){
        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
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
        String rInfo="";
        if (info!=null){
            rInfo=info.getText().toString();
        }
        return rInfo;
    }



    public void setTitle(String title) {
        this.title.setText(title);
    }



    public void setInfo(String msg){
        if(info!=null){
            info.setHint(msg);
        }
    }


    public void setEnableEdit (boolean b){

        if(b) {
            info.setFocusableInTouchMode(true);
            info.setFocusable(true);
            info.requestFocus();
            hideOrOpenKeyBoard();
        }
        else {
            info.setFocusable(false);
            info.setFocusableInTouchMode(false);
        }

    }




}
