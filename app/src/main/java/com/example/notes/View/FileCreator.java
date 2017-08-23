package com.example.notes.View;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.example.ui.R;

/**
 * 分类管理侧滑
 */

public class FileCreator implements com.baoyz.swipemenulistview.SwipeMenuCreator {


    private Context mContext;

    public FileCreator(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 默认文件夹不可删除 所以创建2种菜单
     * @param menu
     */
    @Override
    public void create(SwipeMenu menu) {

        switch (menu.getViewType()) {

            case 0:
                create_0(menu);
                break;
            default:
                create_1(menu);
                break;
        }

    }

    /**
     * 默认文件夹菜单
     * @param menu
     */

    private void create_0(SwipeMenu menu){
        SwipeMenuItem openItem = new SwipeMenuItem(mContext.getApplicationContext());
        openItem.setBackground(R.color.light_blue);
        openItem.setWidth(dp2px(60));
        // openItem.setTitle("清空");
        openItem.setTitleSize(18);
        openItem.setTitleColor(Color.WHITE);
        openItem.setIcon(R.drawable.pic_deleteall);
        menu.addMenuItem(openItem);

    }

    /**
     * 其他文件夹菜单
     * @param menu
     */
    private void create_1(SwipeMenu menu){
        SwipeMenuItem openItem = new SwipeMenuItem(mContext.getApplicationContext());
        openItem.setBackground(R.color.light_blue);
        openItem.setWidth(dp2px(60));
        // openItem.setTitle("编辑");
        openItem.setTitleSize(18);
        openItem.setTitleColor(Color.WHITE);
        openItem.setIcon(R.drawable.pic_edit);
        menu.addMenuItem(openItem);

        SwipeMenuItem deleteItem = new SwipeMenuItem(mContext.getApplicationContext());
        deleteItem.setBackground(R.color.red);
        deleteItem.setWidth(dp2px(60));
        // deleteItem.setTitle("删除");
        deleteItem.setTitleSize(18);
        deleteItem.setTitleColor(Color.WHITE);
        deleteItem.setIcon(R.drawable.pic_delete);
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
