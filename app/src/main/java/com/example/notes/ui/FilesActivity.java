package com.example.notes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.notes.Adapter.FileSwipeAdapter;
import com.example.notes.Dialog.InfoDialog;
import com.example.notes.Interface.onYesOnclickListener;
import com.example.notes.Manager.DBHelper;
import com.example.notes.Manager.DBManager;
import com.example.notes.View.FileCreator;
import com.example.notes.util.MsgToast;
import com.example.notes.util.StringUtil;
import com.example.ui.R;

import java.util.List;


public class FilesActivity extends BaseActivity implements View.OnClickListener {

    private List<String> folderName;
    private SwipeMenuListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);
        init();
    }

    @Override
    protected void onStart(){
        super.onStart();
        viewUpdate();
    }

    private void init(){


            Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

            mToolbar.setNavigationIcon(R.drawable.pic_back);

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });




        //right
        TextView right =(TextView) findViewById(R.id.right_bottom);

        right.setText("新建文件夹");
        right.setOnClickListener(this);

        viewUpdate();

        //list的长按监听
       // mListView.setOnItemLongClickListener(


        mListView.setOnMenuItemClickListener( new SwipeMenuListView.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {//具体实现


                final int postin = position;

                if (postin == 0){
                    final InfoDialog info = new InfoDialog(FilesActivity.this);
                    info.show();
                    info.setTitle("消息提示");
                    info.setInfo("确认清空默认Notes?");
                    info.setEnableEdit(false);
                    info.setYesListener(new onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            dropFolder(postin);
                            info.dismiss();
                        }
                    });
                    return true;
                }

                switch (index){
                    //edit
                    case 0:
                        final InfoDialog dialog = new InfoDialog(FilesActivity.this);
                        dialog.show();

                        final String select_item = folderName.get(position);

                        dialog.setEnableEdit(true);
                        dialog.setTitle("重命名这个类别");
                        dialog.setInfo(select_item);
                        dialog.setYesListener(new onYesOnclickListener() {
                            @Override
                            public void onYesClick() {
                                String newName = dialog.getInfo();
                                if(! newName.isEmpty() ){
                                    updateFolder(folderName.get(postin),newName);
                                }
                                dialog.dismiss();

                            }
                        });
                        break;
                    case 1:
                        final InfoDialog info = new InfoDialog(FilesActivity.this);
                        info.show();
                        info.setTitle("警告");
                        info.setInfo("确认删除这个类别?");
                        info.setEnableEdit(false);
                        info.setYesListener(new onYesOnclickListener() {
                            @Override
                            public void onYesClick() {
                                dropFolder(postin);
                                info.dismiss();
                            }
                        });
                    default:
                        break;
                }
                return true;
            }
        });

        // list 的点击监听
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){

                //获取点击的文件夹名字返回给主界面
                Intent intent = new Intent();
                String  returnData=folderName.get(position);

                intent.putExtra("currentFolderName",returnData);
                setResult(RESULT_OK,intent);
                finish();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right_bottom:
                add();
                break;
            case R.id.back_title:

                //获取点击的文件夹名字返回给主界面
                Intent intent = new Intent();
                intent.putExtra("currentFolderName","Notes");
                setResult(RESULT_OK,intent);
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);


            default:
                break;

        }
    }


    private void updateFolder(String oldName,String newName){
        DBHelper.getInstance(this).update_table(oldName,newName);
        MsgToast.showToast(this,"修改成功");
        viewUpdate();

    }

    private void dropFolder(int position){


        String folder= folderName.get(position);


        DBHelper dbHelper = DBHelper.getInstance(this);
        dbHelper.drop_table(folder);

        MsgToast.showToast(this,"删除成功");
        viewUpdate();
    }

    private void add(){

        final DBHelper dbHelper = DBHelper.getInstance(this);

        final InfoDialog info = new InfoDialog(this);
        info.show();
        info.setTitle("新建类别");
        info.setInfo("输入你喜欢的名字");
        info.setEnableEdit(true);
        info.setYesListener(new onYesOnclickListener() {
            @Override
            public void onYesClick() {
                String newFolder = info.getInfo().trim();

                if(!StringUtil.isEmpty(newFolder)){
                    if(folderName.contains(newFolder)){
                        MsgToast.showToast(FilesActivity.this,"当前已存在该类别");
                        return ;
                    }
                    dbHelper.add_table(newFolder);
                    viewUpdate();
                    MsgToast.showToast(FilesActivity.this,"创建成功");
                }
                info.dismiss();
            }
        });
    }


    private void viewUpdate(){
        //getListViewName
        DBManager dbManager = new DBManager(this);

        folderName = dbManager.getTableName();

        listSort();
        //listView
        mListView = (SwipeMenuListView)findViewById(R.id.list_view);

        //menu
        FileCreator creator = new FileCreator(this);
        mListView.setMenuCreator(creator);

        //adapter
        FileSwipeAdapter adapter = new FileSwipeAdapter(FilesActivity.this,folderName);
        adapter.notifyDataSetChanged();
        //setAdapter
        mListView.setAdapter(adapter);
    }

    private void listSort(){

        folderName.remove("Notes");
        folderName.add(0,"Notes");
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