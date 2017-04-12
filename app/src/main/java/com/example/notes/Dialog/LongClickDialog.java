package com.example.notes.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.notes.Interface.onDeleteclickListener;
import com.example.ui.R;

/**
 * Created by 阿买 on 2017/1/20.
 */

public class LongClickDialog extends InfoDialog {


    private Button detele ;

    private onDeleteclickListener deleteListener;//删除按钮被点击了的监听器

    public LongClickDialog(Context context) {
        super(context);
    }

    /**
     * 初始化
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_delete);
        setCanceledOnTouchOutside(false);
        init();
    }


    /**
     * 初始化
     */
    private  void init(){

        super.initView();
        super.initEvent();

        detele =(Button) findViewById(R.id.delete_dialog);
        detele.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(deleteListener!=null){
                    deleteListener.onDeleteClick();
                }
            }
        } );
    }


    /**
     * 设置监听
     * @param listener
     */
    public  void setDeleteListener (onDeleteclickListener listener){
        this.deleteListener = listener;
    }




}
