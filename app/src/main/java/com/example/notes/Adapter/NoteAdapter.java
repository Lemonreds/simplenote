package com.example.notes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.notes.Model.Note;
import com.example.ui.R;

import java.util.List;

/**
 * Created by 阿买 on 2017/3/21.
 */

public class NoteAdapter extends ArrayAdapter<Note> {

    private Context mContext;


    public NoteAdapter(Context context, List<Note> data){
        super (context,R.layout.item,data);
        this.mContext=context;
    }


    @Override
    public View getView(int position , View convertView , ViewGroup parent) {


        Note note = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item,parent,false);


        TextView txt = (TextView) view.findViewById(R.id.text1_item);
        TextView date = (TextView) view.findViewById(R.id.text2_item);

        if(txt!=null)txt.setText(note.getName());
        if(date!=null)date.setText(note.getDate().getEasyDate());


        return view;

    }
}

