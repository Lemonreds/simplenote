package com.example.notes.Manager;


import android.content.Context;
import android.widget.BaseAdapter;

import com.example.notes.Dialog.InfoDialog;
import com.example.notes.Dialog.MyOnClickListener;
import com.example.notes.View.MsgToast;
import com.example.notes.Activity.RecycleActivity;
import com.example.notes.Model.Note;


import java.util.List;

/**
 * 回收站管理
 */
public class RecycleManager {

    private Context mContext;
    private List<Note> mData;
    private String currentFolderName;
    private BaseAdapter adapter;

    private DBManager dbManager;

    public RecycleManager( Context mContext, List<Note> list,BaseAdapter adapter) {
        this.mContext = mContext;
        this.dbManager = new DBManager(mContext);
        this.mData = list;
        this.currentFolderName = "recycle";
        this.adapter = adapter;
    }

    /**
     * 删除
     * @param position
     */
    public void delete(int position){

        dbManager.delete(currentFolderName,mData.get(position));
        update_bottom(position);

    }

    /**
     * 恢复
     * @param position
     */

    public  void recovery(int position){


        Note note = mData.get(position);
        dbManager.recovery(note);
        update_bottom(position);
    }

    /**
     * 恢复所有
     */
    public  void recoveryAll(){

        if(mData.size() == 0 ){
            MsgToast.showToast(mContext,"空空如也");
            return;
        }

       for(int i=0;i<mData.size();){
           recovery(i);
       }
    }

    /**
     * 删除所有
     */
    public void clearAll(){


        int dataSize = mData.size();


        if(dataSize == 0 ){
            MsgToast.showToast(mContext,"空空如也");
            return;
        }


        final InfoDialog warnDialog = new InfoDialog(mContext);
        warnDialog.show();
        warnDialog.setTitle("警告");
        warnDialog.setEnableEdit(false);
        warnDialog.setInfo("删除不可恢复!");
        warnDialog.setYesListener(new MyOnClickListener() {
            @Override
            public void onClick() {
                int number  = dbManager.clearAllFolder(currentFolderName);
                update_bottom(-1);
                MsgToast.showToast(mContext,"删除了 "+number+" 条");
                ((RecycleActivity)mContext).finish();
            }
        });
    }

    /**
     * 底部栏更新
     * @param position
     */
    private  void update_bottom(int position){

        if(position!=-1) {
            mData.remove(position);
        }
        adapter.notifyDataSetChanged();
        ((RecycleActivity)mContext).update_bottom();
        if(mData.size()==0){
            ((RecycleActivity)mContext).finish();
        }
    }


}
