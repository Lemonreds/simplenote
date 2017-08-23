package com.example.notes.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.notes.Adapter.RecycleSwipeAdapter;
import com.example.notes.Manager.DBManager;
import com.example.notes.Manager.RecycleManager;
import com.example.notes.View.MsgToast;
import com.example.notes.View.RecycleCreator;
import com.example.notes.Model.Note;
import com.example.ui.R;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleActivity extends BaseActivity{

    private SwipeMenuListView mListView;
    private List<Note> mData;
    private RecycleManager mManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.pic_back);//设置导航栏图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

                CircleImageView deleteAll =(CircleImageView) findViewById(R.id.deleteAll);
                deleteAll.setVisibility(View.VISIBLE);
                deleteAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mData.size()==0){
                            MsgToast.showToast(RecycleActivity.this,getResources().getString(R.string.empty_info));
                            return;
                        }
                        mManager.clearAll();
                    }
                });

                CircleImageView recoverAll =(CircleImageView) findViewById(R.id.recoverAll);
                recoverAll.setVisibility(View.VISIBLE);
                recoverAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mData.size()==0){
                            MsgToast.showToast(RecycleActivity.this,getResources().getString(R.string.empty_info));
                            return;
                        }
                        mManager.recoveryAll();

                    }
                });


        mData = new DBManager(this).search("recycle");



        RecycleSwipeAdapter adapter = new RecycleSwipeAdapter(this,mData);


        RecycleCreator creator = new RecycleCreator(this);

        mListView =(SwipeMenuListView) findViewById(R.id.list_view);
        mListView.setMenuCreator(creator);
        mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        mListView.setAdapter(adapter);

        mManager = new RecycleManager(this,mData,adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //恢复后才能打开
                MsgToast.showToast(RecycleActivity.this,getResources().getString(R.string.recovery_recycle));
            }
        });

        view_Listener();
        update_bottom();

    }




    public  void view_Listener(){

            mListView.setOnMenuItemClickListener( new SwipeMenuListView.OnMenuItemClickListener(){

                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {//具体实现

                    switch (index){
                        case 0:
                            mManager.recovery(position);
                            break;
                        case 1:
                            mManager.delete(position);
                        default:
                            break;
                    }
                    return true;
                }
            });
    }


    /**
     * 底部栏的更新
     */
    public void update_bottom(){


        TextView text_bottom = (TextView) findViewById(R.id.text_bottom);

        StringBuilder str = new StringBuilder();


        int number = 0;
        if(mData!=null){
            number=mData.size();
        }

        if(number == 0){
            text_bottom.setVisibility(View.GONE);

            mListView.setVisibility(View.GONE);

            RelativeLayout empty = (RelativeLayout) findViewById(R.id.empty);
            empty.setVisibility(View.VISIBLE);
            TextView info = (TextView) findViewById(R.id.text_empty);
            //回收站似乎空空如也
            info.setText(getResources().getString(R.string.recycle_empty_info));

            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.bottom_content);
            linearLayout.setVisibility(View.GONE);

            return ;
        }else{

            mListView.setVisibility(View.VISIBLE);
            RelativeLayout empty = (RelativeLayout) findViewById(R.id.empty);
            empty.setVisibility(View.GONE);
            str.append(number);
            str.append(" 个备忘录");

            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.bottom_content);
            linearLayout.setVisibility(View.VISIBLE);
        }


        text_bottom.setText(str.toString());
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        switch(keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}
