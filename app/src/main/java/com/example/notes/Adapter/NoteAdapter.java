package com.example.notes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.notes.model.Note;
import com.example.ui.R;

import java.util.List;

/**
 * Created by 阿买 on 2017/3/21.
 */

public class NoteAdapter extends ArrayAdapter<Note> {

    private int note_xml_id;
    private Context mContext;


    public NoteAdapter(Context context, int item_xml_id, List<Note> data){
        super (context,item_xml_id,data);
        this.mContext=context;
        this.note_xml_id = item_xml_id;

    }


    @Override
    public View getView(int position , View convertView , ViewGroup parent) {


        Note note = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(note_xml_id,parent,false);


        TextView txt = (TextView) view.findViewById(R.id.text1_item);
        TextView date = (TextView) view.findViewById(R.id.text2_item);

        if(txt!=null)txt.setText(note.getName());
        if(date!=null)date.setText(note.getDate().getEasyDate());


        return view;

    }
}

