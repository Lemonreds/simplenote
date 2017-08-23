package com.example.notes.Dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.ui.R;

/**
 * 进度框
 */

public class ProDialog extends ProgressDialog{

    private String text;

    public ProDialog(Context context,String text) {
        super(context,R.style.ProgressDialog);
        this.text=text;
    }



    @Override
    protected void onCreate(Bundle  savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        init(getContext(),text);
    }

    private void init(Context context,String text){

        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.dialog_progress);

        TextView info = (TextView) findViewById(R.id.tv_load_dialog);
        info.setText(text);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void show()
    {
        super.show();
    }
}
