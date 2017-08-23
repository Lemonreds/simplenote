package com.example.notes.View;

/**
 * Toast剧中提示
 */

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ui.R;


public class MsgToast {

    /**
     * 提示信息
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {

            TextView mTextView;

            //加载Toast布局
            View toastRoot = LayoutInflater.from(context).inflate(R.layout.msg_toast, null);

            //初始化布局控件
            mTextView = (TextView) toastRoot.findViewById(R.id.msg_toast);

            //为控件设置属性
            mTextView.setText(message);

            //Toast的初始化
            Toast toastStart = new Toast(context);

            //获取屏幕高度
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int height = wm.getDefaultDisplay().getHeight();

            //Toast的Y坐标是屏幕高度的1/2
            toastStart.setGravity(Gravity.TOP, 0, height / 2);
            toastStart.setDuration(Toast.LENGTH_SHORT);
            toastStart.setView(toastRoot);
            toastStart.show();
        }

}
