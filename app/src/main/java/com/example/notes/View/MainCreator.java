package com.example.notes.View;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.example.ui.R;

/**
 * 主界面侧滑菜单
 */

public class MainCreator implements com.baoyz.swipemenulistview.SwipeMenuCreator {


    private Context mContext;



    public MainCreator(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 初始化且创建菜单
     * @param menu
     */
    @Override
    public void create(SwipeMenu menu) {

        SwipeMenuItem openItem = new SwipeMenuItem(mContext.getApplicationContext());

        openItem.setBackground(R.color.orange);
        openItem.setWidth(dp2px(55));
       // openItem.setTitle("编辑");
        openItem.setIcon(R.drawable.pic_edit);
        //openItem.setTitleSize(16);
        openItem.setTitleColor(Color.WHITE);
        menu.addMenuItem(openItem);


        SwipeMenuItem moveItem = new SwipeMenuItem(mContext.getApplicationContext());
        moveItem.setBackground(R.color.gray);
        moveItem.setWidth(dp2px(55));
       // moveItem.setTitle("移动");
       moveItem.setIcon(R.drawable.pic_move);
       // moveItem.setTitleSize(16);
        moveItem.setTitleColor(Color.WHITE);
        menu.addMenuItem(moveItem);


        SwipeMenuItem deleteItem = new SwipeMenuItem(mContext.getApplicationContext());
        deleteItem.setBackground(R.color.deep_red);
        deleteItem.setWidth(dp2px(55));
        deleteItem.setIcon(R.drawable.pic_delete);
       // deleteItem.setTitle("删除");

      //  deleteItem.setTitleSize(16);
        deleteItem.setTitleColor(Color.WHITE);
        menu.addMenuItem(deleteItem);

        }

    // 将dp转换为px
    private int dp2px(int value) {
        // 第一个参数为我们待转的数据的单位，此处为 dp（dip）
        // 第二个参数为我们待转的数据的值的大小
        // 第三个参数为此次转换使用的显示量度（Metrics），它提供屏幕显示密度（density）和缩放信息
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                mContext.getResources().getDisplayMetrics());
    }


}
