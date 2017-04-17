package com.example.notes.ui;

import android.content.Intent;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.*;
import com.example.notes.Dialog.HeadDialog;
import com.example.notes.Dialog.ProDialog;
import com.example.notes.Interface.MyOnClickListener;
import com.example.notes.Manager.DBManager;

import com.example.notes.Manager.NoteManager;
import com.example.notes.View.MainCreator;

import com.example.notes.View.MainScrollview;
import com.example.notes.View.SwipeListView;
import com.example.notes.util.MsgToast;
import com.example.notes.util.ComparatorUtil;
import com.example.notes.util.Note;

import com.example.notes.util.PersonalInfoUtil;
import com.example.notes.util.StringUtil;
import com.example.notes.Adapter.MainSwipeAdapter;
import com.example.ui.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;


import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity {



    private NoteManager noteManager;

    private SwipeListView mListView;
    private List<Note> mData;

    private DrawerLayout mDrawer;
    private NavigationView navigation;

    private String currentFolderName ="Notes";
    private Toolbar toolbar;

    private TextView toolbar_title;
    private long backpressFirst = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionbarReset();
        init();
    }


    @Override
    protected void onResume() {
        super.onResume();
        listView_setting();
    }

    protected void onStart(){
        super.onStart();
        listView_setting();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 主界面的初始化
     */

    public void init (){

        listView_setting();
        slide_setting();
        fab_setting();
    }

    private void slide_setting(){

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigation = (NavigationView)findViewById(R.id.navigation);


        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {


                switch (item.getItemId()){

                    case R.id.nav_folder:
                        Intent intent = new Intent(MainActivity.this,FilesActivity.class);
                        startActivityForResult(intent,1);
                        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                        break;
                    case R.id.nav_recycle:
                        Intent intent1 = new Intent(MainActivity.this,RecycleActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                        break;
                 //  case R.id.nav_setting:
                  //     Intent intent2 = new Intent(MainActivity.this,SettingActivity.class);
                 //      startActivity(intent2);
                  //     overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                  //   break;
                    case R.id.nav_about:
                        Intent intent3 = new Intent(MainActivity.this,AboutActivity.class);
                        startActivity(intent3);
                        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                        break;
                    case R.id.nav_exit:
                        System.exit(0);
                        break;
                }
                mDrawer.closeDrawers();
                return false;
            }
        });

        Resources resource=getBaseContext().getResources();
        ColorStateList csl=resource.getColorStateList(R.color.item_color_navgtin);
        navigation.setItemTextColor(csl);

        View view = navigation.inflateHeaderView(R.layout.nav_head);
        personalSet(view);
    }


    public void actionbarReset(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.pic_home);//设置导航栏图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               mDrawer.openDrawer(GravityCompat.START);
           }
        });

        toolbar_title = (TextView) findViewById(R.id.title_toolbar) ;
        toolbar_title.setText(currentFolderName);

        toolbar.inflateMenu(R.menu.menu_main);//设置右上角的填充菜单

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){

                    case R.id.search_item:
                        Intent intent1 = new Intent(MainActivity.this,SearchActivity.class);
                        intent1.putExtra("currentFolderName",currentFolderName);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                    break;
                }
                return false;
            }
        });
    }


    private void personalSet(View view){


        final TextView userName = (TextView) view.findViewById(R.id.useName_nav);

        final CircleImageView userImg = (CircleImageView) view.findViewById(R.id.useImg_nav);

        final ImageView userEdit = (ImageView)view.findViewById(R.id.useEdit_nav);

        final PersonalInfoUtil personal = new PersonalInfoUtil(this);
        final String name = personal.getPersonName();
        userName.setText(name);





        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HeadDialog dialog = new HeadDialog(MainActivity.this);
                dialog.show();

                dialog.setPersonalName(userName.getText().toString());

                dialog.setYesListener(new MyOnClickListener(){
                    @Override
                    public void onClick() {
                        String newName = dialog.getPersonalName();
                        if(! newName.equals(name)){
                            personal.savePersonName(newName);
                            userName.setText(newName);
                        }
                        dialog.dismiss();
                    }
                });
                mDrawer.closeDrawers();
            }
        };


        userImg.setOnClickListener(listener);
        userName.setOnClickListener(listener);
        userEdit.setOnClickListener(listener);

    }


    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case  1:
                if( resultCode == RESULT_OK ) {
                    String returnData = data.getStringExtra("currentFolderName");
                    if (!StringUtil.isEmpty(returnData)) {
                        currentFolderName = returnData;
                        toolbar_title.setText(currentFolderName);
                    }
                }
                listView_setting();

                break;
            default:
                break;
        }

    }



    private void hide_fabMenu(){
        //关闭fab菜单
        FloatingActionsMenu menu = (FloatingActionsMenu)findViewById(R.id.action_menu);
        if(menu!=null) menu.collapse();
    }


    public void listView_setting(){

        hide_fabMenu();

        final ProDialog proDialog = new ProDialog(this, "正在加载...");
        proDialog.show();

        mData = new DBManager(this).search(currentFolderName);

        Collections.sort(mData, new ComparatorUtil());

        MainSwipeAdapter adapter = new MainSwipeAdapter(this,mData);

        noteManager = new NoteManager(this, currentFolderName,mData,adapter);

        MainCreator mainCreator = new MainCreator(this);

        mListView =(SwipeListView) findViewById(R.id.list_view);
        mListView.setMenuCreator(mainCreator);
        mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        mListView.setAdapter(adapter);

        MainScrollview scrollview = (MainScrollview) findViewById(R.id.main_scrollView);
        scrollview.setOnScrollListener(new MainScrollview.ScrollViewListener() {
            @Override
            public void onScroll(int dy) {
                if (dy > 0) {//下滑
                    showOrHideFab(false);
                } else if (dy <= 0 ) {//上滑
                    showOrHideFab(true);
                }
            }
        });

        view_Listener();
        emptyListCheck();
        proDialog.dismiss();
    }


    private void fab_setting(){


        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.action_note);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //noteManager.add();
                //关闭fab菜单
                FloatingActionsMenu menu = (FloatingActionsMenu)findViewById(R.id.action_menu);
                menu.collapse();

                Intent intent = new Intent(MainActivity.this,CreateActivity.class);
                intent.putExtra("currentFolderName",currentFolderName);
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);


            }
        });

        FloatingActionButton fab_quick = (FloatingActionButton)findViewById(R.id.action_quick);

        fab_quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //noteManager.add();
                //关闭fab菜单
                FloatingActionsMenu menu = (FloatingActionsMenu)findViewById(R.id.action_menu);
                menu.collapse();

                Intent intent = new Intent(MainActivity.this,QuickCreateActivity.class);
                intent.putExtra("currentFolderName",currentFolderName);
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
    }


    private void showOrHideFab(boolean show){

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.action_menu);

        if(show){
            fab.setVisibility(View.VISIBLE);
        }else{
            fab.setVisibility(View.GONE);
        }

    }

    public void view_Listener() {

        //点击监听
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                noteManager.ItemClick(position);

            }
        });


        mListView.setOnMenuItemClickListener( new SwipeMenuListView.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {//具体实现

                switch (index){
                    //edit
                    case 0:
                        noteManager.editClick(position);
                        break;
                    case 1:
                        Intent intent = new Intent(MainActivity.this,FilesActivity.class);
                        intent.putExtra("move",true);

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("note",mData.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);

                        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                        //move
                        break;
                    case 2:
                        noteManager.deleteClick(position);
                    default:
                        break;
                }
                return true;
            }
        });

    }


    public void emptyListCheck(){



        int number = 0;
        if(mData!=null){
            number=mData.size();
        }


        if(number == 0) {
            //hide and show
            mListView.setVisibility(View.GONE);
            RelativeLayout empty = (RelativeLayout) findViewById(R.id.empty);
            empty.setVisibility(View.VISIBLE);

            TextView info = (TextView) findViewById(R.id.text_empty);
            info.setText("似乎空空如也");
        }else{
            mListView.setVisibility(View.VISIBLE);
            RelativeLayout empty = (RelativeLayout) findViewById(R.id.empty);
            empty.setVisibility(View.GONE);
        }
    }



    /**

    public void update_bottom(){




        TextView  text_bottom = (TextView) findViewById(R.id.text_bottom);

        StringBuilder str = new StringBuilder();


        int number = 0;
        if(mData!=null){
            number=mData.size();
        }



        if(number == 0){

            //hide and show
            mListView.setVisibility(View.GONE);
            TextView tv = (TextView) findViewById(R.id.empty_view);
            tv.setVisibility(View.VISIBLE);


            str.append("无备忘录");
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


**/


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                long backPressSecond = System.currentTimeMillis();
                if (backPressSecond - backpressFirst > 2000) { //如果两次按键时间间隔大于2秒，则不退出
                    MsgToast.showToast(this,"再按一次返回键退出应用");
                    backpressFirst = backPressSecond;//更新firstTime

                    return true;
                } else {  //两次按键小于2秒时，退出应用
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }




}


