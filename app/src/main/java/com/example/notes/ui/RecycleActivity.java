package com.example.notes.ui;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.notes.Adapter.RecycleSwipeAdapter;
import com.example.notes.Manager.DBManager;
import com.example.notes.Manager.RecycleManager;
import com.example.notes.util.MsgToast;
import com.example.notes.View.RecycleCreator;
import com.example.notes.util.Note;
import com.example.ui.R;
import java.util.List;

public class RecycleActivity extends BaseActivity implements View.OnClickListener{

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
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });
        toolbar.inflateMenu(R.menu.menu_recycle);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mManager.clearAll(mData.size());
                return false;
            }
        });




        mData = new DBManager(this).search("recycle");
        RecycleSwipeAdapter adapter = new RecycleSwipeAdapter(this,mData);


        RecycleCreator creator = new RecycleCreator(this);

        mListView =(SwipeMenuListView) findViewById(R.id.list_view);
        mListView.setMenuCreator(creator);
        mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MsgToast.showToast(RecycleActivity.this,"恢复后才能打开");
            }
        });


        mManager = new RecycleManager(this,mData,adapter);

        view_Listener();
        update_bottom();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){


            case R.id.back_title:
                finish();
                break;
            default:
                break;

        }
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

            //hide and show
            mListView.setVisibility(View.GONE);
            TextView tv = (TextView) findViewById(R.id.empty_view);
            tv.setVisibility(View.VISIBLE);

            return ;

          //  str.append("无备忘录");
        }else{

            //hide and show
            mListView.setVisibility(View.VISIBLE);
            TextView tv = (TextView) findViewById(R.id.empty_view);
            tv.setVisibility(View.GONE);
            str.append(number);

            str.append(" 个备忘录");
        }


        text_bottom.setText(str.toString());
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}
