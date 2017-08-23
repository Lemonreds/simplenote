package com.example.notes.Manager;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.BaseAdapter;

import com.example.notes.Dialog.EditDialog;
import com.example.notes.Dialog.InfoDialog;
import com.example.notes.Dialog.NoteDialog;
import com.example.notes.Dialog.MyOnClickListener;
import com.example.notes.View.MsgToast;
import com.example.notes.Activity.ContentActivity;
import com.example.notes.Model.Date;

import com.example.notes.Model.Note;
import com.example.notes.Util.StringUtil;
import com.example.ui.R;

import java.util.List;

/**
 * Note管理类
 */

public class NoteManager{

    private Context mContext;
    private List<Note> list;

    private String currentFolderName;//Note的所属文件夹
    private BaseAdapter adapter; //适配器
    private DBManager dbManager;//数据库管理类



    public NoteManager(Context context,String currentFolderName){
        this.mContext=context;
        this.currentFolderName=currentFolderName;
        dbManager=new DBManager(mContext);
    }

    public NoteManager(Context context,String currentFolderName,
                           List<Note> list,BaseAdapter adapter){
        this(context,currentFolderName);
        this.list=list;
        this.adapter=adapter;
    }

    /**
     * 第一次使用说明
     */
    public void addDescription(){

        Note description = new Note(mContext.getResources().getString(R.string.title_des),new Date(),
                mContext.getResources().getString(R.string.app_name),
                mContext.getResources().getString(R.string.content_des),
                currentFolderName);
        add(description);
    }

    /**
     * listView的短点击事件
     * @param position
     */
    public void ItemClick(int position){
        final Note select_item = list.get(position);
        ItemClick(select_item);
    }

    /**
     * 打开note
     * @param select_item
     */
    private void ItemClick(Note select_item){

        Intent intent = new Intent(mContext,ContentActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("note", select_item);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    /**
     * 编辑操作
     * @param position
     */
    public void editClick(int position){


        final EditDialog dialog = new EditDialog(mContext);
        dialog.show();

        final Note select_item = list.get(position);

        dialog.setTitle("编辑");
        dialog.setInfo(select_item.getName());

        dialog.setYesListener(new MyOnClickListener() {
            @Override
            public void onClick() {
                String newName = dialog.getInfo();

                if(StringUtil.isEmpty(newName.trim())){
                    MsgToast.showToast(mContext,"名字不能为空");
                }else{
                    update(select_item,newName,dialog.getLevel());
                }
                dialog.dismiss();
            }
        });
    }

    /**
     * 点击了删除
     * @param position
     */
    public void deleteClick(int position){


        Note select_item = list.get(position);
        delete(select_item);
        MsgToast.showToast(mContext,"已移至回收站");

    }

    /**
     * 新建Note
     * @param note
     */
    public void add(Note note){
        dbManager.insert(currentFolderName,note);
    }

    /**
     * 数据库交互
     * @param note
     */
    private void delete(Note note) {
        list.remove(note);

        adapter.notifyDataSetChanged();

        dbManager.delete(currentFolderName,note);
    }

    /**
     * 删除Note
     * @param note
     */
    public void deleteNote(Note note) {

        final Note note1 = note;
        dbManager.delete(currentFolderName,note1);

    }

    /**
     * 数据库更新
     * @param preNote
     * @param newNote
     */
    public void update(Note preNote,Note newNote){
        dbManager.upDate(currentFolderName,preNote,newNote);
    }

    /**
     * 更新名字/level
     * @param note
     * @param newName
     * @param newLevel
     */
    private void update(Note note,String newName,int newLevel){

        Note newNote = note.getClone();
        newNote.setName(newName);
        newNote.setLevel(newLevel);


        dbManager.upDate(currentFolderName,note,newNote);

        if(list!=null) {
            int index = list.indexOf(note);
            list.set(index, newNote);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 更新地理位置
     * @param note
     * @param location
     * @return
     */
    public Note updateLocation(Note note ,String location){



        Note newNote = note.getClone();

        newNote.setLocation(location);
        dbManager.upDate(currentFolderName,note,newNote);
        return newNote;
    }

}
