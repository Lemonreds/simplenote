package com.example.notes.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ui.R;

import java.util.List;

/**
 * 搜索历史适配器
 */

public class HistoryAdapter extends ArrayAdapter<String> {


    private Context mContext;
    private List<String> mData;

    public HistoryAdapter(Context context, List<String> data){
        super (context,R.layout.item_history,data);
        this.mContext=context;
        this.mData=data;
    }

    @Override
    public View getView(final int position , View convertView , final ViewGroup parent) {


        String history = getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_history,parent,false);

        TextView txt = (TextView) view.findViewById(R.id.text_his_item);
        txt.setText(history);


        ImageView delete =(ImageView)view.findViewById(R.id.delete_his_item);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  MsgToast.showToast(mContext,"Click "+position);
                remove(mData.get(position));
                saveHistory();
            }
        });
        return view;
    }



    private void saveHistory(){


        StringBuilder sb = new StringBuilder();

        SharedPreferences.Editor editor =
                mContext.getSharedPreferences("history",Context.MODE_PRIVATE).edit();

        for (String str: mData){
            sb.append(str);
            sb.append("|");
        }

        editor.putString("data_history",sb.toString());
        editor.apply();
    }
}

