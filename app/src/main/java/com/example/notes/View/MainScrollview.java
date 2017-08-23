package com.example.notes.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 主界面的滑动
 */

public class MainScrollview extends ScrollView {


   private ScrollViewListener scrollViewListener = null;

    public MainScrollview(Context context) {
        super(context);
    }

    public MainScrollview(Context context, AttributeSet attrs,
                          int defStyle) {
        super(context, attrs, defStyle);
    }

    public MainScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScrollListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }



    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {

        super.onScrollChanged(x, y, oldx, oldy);


        if (scrollViewListener != null) {

            if (oldy < y && ((y - oldy) > 15)) {//滑动距离超过15像素 控件向上滑动
                scrollViewListener.onScroll(y - oldy);

            } else if (oldy > y && (oldy - y) > 15) {// 滑动距离超过15像素  向下滑动
                scrollViewListener.onScroll(y - oldy);
            }

        }
    }

    public  interface ScrollViewListener{//dy Y轴滑动距离
        void onScroll(int dy);
    }
}