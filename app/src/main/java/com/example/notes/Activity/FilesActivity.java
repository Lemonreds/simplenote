package com.example.notes.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.notes.Adapter.FileSwipeAdapter;
import com.example.notes.Dialog.ChooseDialog;
import com.example.notes.Dialog.InfoDialog;
import com.example.notes.Dialog.MyOnClickListener;
import com.example.notes.Manager.DBHelper;
import com.example.notes.Manager.DBManager;
import com.example.notes.View.FileCreator;
import com.example.notes.View.MsgToast;
import com.example.notes.Model.Note;
import com.example.notes.Util.StringUtil;
import com.example.ui.R;

import java.util.List;

/**
 * 文件夹
 */

public class FilesActivity extends BaseActivity implements View.OnClickListener {

    private List<String> folderName;
    private SwipeMenuListView mListView;

    private boolean addFolder;//记录是否打开了新建窗口 true为打开

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

    private void init() {


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.pic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });


        findViewById(R.id.add_file).setOnClickListener(this);
        viewUpdate();


        boolean isMove = getIntent().getBooleanExtra("move", false);

        if (isMove) {
            final Note moveNote = (Note) getIntent().getSerializableExtra("note");

            TextView title = (TextView) findViewById(R.id.title_toolbar);
            title.setText("选择类别");
            TextView text = (TextView) findViewById(R.id.text_files);
            text.setVisibility(View.VISIBLE);
            text.setText("将备忘录 " + moveNote.getName() + " 移动到...");


            // list 的点击监听
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String chooseFolder = folderName.get(position);

                    DBManager dbManager = new DBManager(FilesActivity.this);
                    dbManager.moveToFolder(chooseFolder,moveNote);
                    finish();
                }
            });


        } else {

            // list 的点击监听
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if(addFolder)return;
                    //获取点击的文件夹名字返回给主界面
                    Intent intent = new Intent();
                    String returnData = folderName.get(position);
                    intent.putExtra("currentFolderName", returnData);
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                }
            });
        }



        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {//具体实现


                final int postin = position;

                if (postin == 0) {
                    final InfoDialog info = new InfoDialog(FilesActivity.this);
                    info.show();
                    info.setTitle("消息提示");
                    info.setInfo("确认清空 Notes ?");
                    info.setEnableEdit(false);
                    info.setYesListener(new MyOnClickListener() {
                        @Override
                        public void onClick() {
                            dropFolder(postin);
                            info.dismiss();
                        }
                    });
                    return true;
                }

                switch (index) {
                    //edit
                    case 0:
                        final InfoDialog dialog = new InfoDialog(FilesActivity.this);
                        dialog.show();

                        final String select_item = folderName.get(position);

                        dialog.setEnableEdit(true);
                        dialog.setTitle("重命名这个类别");
                        dialog.setInfo(select_item);
                        dialog.setYesListener(new MyOnClickListener() {
                            @Override
                            public void onClick() {
                                String newName = dialog.getInfo();
                                if (!newName.isEmpty()) {
                                    updateFolder(folderName.get(postin), newName);
                                }
                                dialog.dismiss();

                            }
                        });
                        break;
                    case 1:

                        final ChooseDialog deleteDialog = new ChooseDialog(FilesActivity.this);

                        deleteDialog.show();
                        deleteDialog.setTitle("删除分类?");
                        deleteDialog.setInfo("如果仅删除文件夹,其所有的备忘录都将转移到回收站.");
                        deleteDialog.setChoose1("删除文件夹和备忘录");
                        deleteDialog.setListener_1(new MyOnClickListener() {
                            @Override
                            public void onClick() {
                                dropFolderAndNote(postin);
                            }
                        });
                        deleteDialog.setChoose2("仅删除文件夹");
                        deleteDialog.setListener_2(new MyOnClickListener() {
                            @Override
                            public void onClick() {
                                dropFolder(postin);
                            }
                        });
                        deleteDialog.setChoose3("取消");

                        break;

                    default:
                        break;
                }
                return true;
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_file:
                add();
                break;
            default:
                break;

        }
    }


    private void updateFolder(String oldName,String newName){
        DBHelper.getInstance(this).update_table(oldName,newName);
        MsgToast.showToast(this,"修改成功");
        viewUpdate();

    }

    private void dropFolderAndNote(int position){

        String folder= folderName.get(position);
        DBHelper dbHelper = DBHelper.getInstance(this);
        dbHelper.drop_table_deep(folder);
        MsgToast.showToast(this,"删除成功");
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


        addFolder=true;
        final InfoDialog info = new InfoDialog(this);
        info.show();
        info.setTitle("新的分类");
        info.setInfo("输入你喜欢的名字");
        info.setEnableEdit(true);
        info.setYesListener(new MyOnClickListener() {
            @Override
            public void onClick() {
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
        addFolder=false;
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
        //botton
        TextView bottom= (TextView)findViewById(R.id.text_bottom);
        bottom.setText(" "+folderName.size()+" ");

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
