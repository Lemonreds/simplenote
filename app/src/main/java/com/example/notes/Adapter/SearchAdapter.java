package com.example.notes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.notes.Model.Note;
import com.example.notes.Util.StringUtil;
import com.example.ui.R;

import java.util.List;

/**
 * 搜索界面适配器
 */

public class SearchAdapter  extends ArrayAdapter<Note> {

    private Context mContext;


    public SearchAdapter(Context context, List<Note> data){
        super (context, R.layout.item_search,data);
        this.mContext=context;
    }


    @Override
    public View getView(int position , View convertView , ViewGroup parent) {


        Note note = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_search,parent,false);


        TextView txt = (TextView) view.findViewById(R.id.text1_item);
        TextView date = (TextView) view.findViewById(R.id.text2_item);
        TextView content = (TextView)view.findViewById(R.id.content_item);

        if(txt!=null)txt.setText(note.getName());
        if(date!=null)date.setText(note.getDate().getEasyDate());

        String simpleContent = StringUtil.clearHtml(note.getText());
        if (simpleContent.length()<50){
            content.setText(simpleContent);
        }else{
            content.setText(simpleContent.substring(0,49));
        }

        return view;

    }
}



