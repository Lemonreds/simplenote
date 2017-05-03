package com.example.notes.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.notes.model.Date;
import com.example.notes.model.Note;
import com.example.ui.R;

import java.util.List;

/**
 * Created by 阿买 on 2017/4/6.
 */

public class RecycleSwipeAdapter extends BaseAdapter {


    private List<Note> mData ;
    private Context mContext;

    private Date nowDate;
    public RecycleSwipeAdapter(Context mContext, List<Note> mData) {
        this.mContext = mContext;
        this.mData = mData;
        nowDate = new Date();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(null == convertView) {
            convertView = View.inflate(mContext, R.layout.item_recycle, null);
        }
        Note note = ((Note)getItem(position));


       //// TextView txt = (TextView) convertView.findViewById(R.id.text1_item);
       // TextView deleteDate = (TextView) convertView.findViewById(R.id.text2_item);
       // deleteDate.setTextColor(mContext.getResources().getColor(R.color.red));

        TextView name =(TextView)convertView.findViewById(R.id.name_item);
        TextView folder =(TextView)convertView.findViewById(R.id.folder_item);
        TextView deleteDate = (TextView) convertView.findViewById(R.id.date_item);

        //if(txt!=null)txt.setText(note.getName());
        name.setText(note.getName());
        folder.setText(note.getFolderName());

        int days=nowDate.getLeaveDay(note.getDate());
        String leaveDay = Integer.toString(30-days)+"天";

        if(deleteDate!=null)deleteDate.setText(leaveDay);

        return convertView;
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


}
