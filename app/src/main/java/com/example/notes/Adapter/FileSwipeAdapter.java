package com.example.notes.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.notes.Manager.DBManager;
import com.example.ui.R;

import java.util.List;

/**
 * 文件夹管理的菜单
 */

public class FileSwipeAdapter extends BaseAdapter {


    private List<String> mData ;
    private Context mContext;

    public FileSwipeAdapter(Context mContext, List<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(null == convertView) {
            convertView = View.inflate(mContext, R.layout.item, null);
        }

        String str=(String) getItem(position);

        int length = getFolderLength(str);

        TextView folderName=(TextView)convertView.findViewById(R.id.text1_item);
        TextView folderLength=(TextView)convertView.findViewById(R.id.text2_item);


        if(folderName!=null)folderName.setText(str);
        if(folderLength!=null)folderLength.setText(Integer.toString(length));


        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private int getFolderLength(String folderName){

        DBManager dbManager= new DBManager(mContext);
        return  dbManager.getTableLength(folderName);
    }
}
