package com.example.notes.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.notes.Manager.DBManager;
import com.example.notes.Manager.NoteManager;
import com.example.notes.model.Note;
import com.example.notes.Adapter.NoteAdapter;
import com.example.notes.util.StringUtil;
import com.example.ui.R;


import java.util.ArrayList;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private SearchView searchView;


    private Button text1 ;
    private TextView delete1 ;

    private Button text2 ;
    private TextView delete2 ;

    private Button text3 ;
    private TextView delete3 ;

    private Button clear;


    private ArrayList<Note> list_search ;
    private ArrayList<Note> list_result ;

    private String currentName;

    private ListView listView ;
    private NoteAdapter adapter ;

    private int visibleNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        currentName = getIntent().getStringExtra("currentFolderName");

        initSearchHistory();
        init();
    }


    @Override
    protected void onStart(){
        super.onStart();
        init();
        update_Bottom();
        clearSearchHint();
    }

    /**
     * 返回main
     */
    private void reBack(){

        saveHistory("");
        Intent intent = new Intent(SearchActivity.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    private void initSearchHistory(){
        text1 = (Button) findViewById(R.id.text1_history);
        text1.setOnClickListener(this);
        delete1 = (TextView) findViewById(R.id.del1_history);
        delete1.setOnClickListener(this);

        text2 = (Button) findViewById(R.id.text2_history);
        text2.setOnClickListener(this);
        delete2 = (TextView) findViewById(R.id.del2_history);
        delete2.setOnClickListener(this);

        text3 = (Button) findViewById(R.id.text3_history);
        text3.setOnClickListener(this);
        delete3 = (TextView) findViewById(R.id.del3_history);
        delete3.setOnClickListener(this);

        clear =(Button) findViewById(R.id.clear_history);
        clear.setOnClickListener(this);

    }

    public void init(){


        DBManager dbManager = new DBManager(this);
        list_search = dbManager.search(currentName);
        list_result = new ArrayList<>();

        listView = (ListView) findViewById(R.id.content_lis_search);

        adapter = new NoteAdapter(SearchActivity.this,R.layout.item,list_result);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoteManager manager=new NoteManager
                        (SearchActivity.this,currentName,list_result,adapter);

                manager.ItemClick(position);
                saveHistory(searchView.getQuery().toString());
            }
        });


        getHistory();

        searchView = (SearchView) findViewById(R.id.input_sv_search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                // 当点击搜索按钮时触发该方法
                @Override
                public boolean onQueryTextSubmit(String query) {
                    saveHistory(query);
                    getHistory();
                    update_Bottom();
                    hideOrOpenKeyBoard();
                    return false;
                }

                // 当搜索内容改变时触发该方法
                @Override
                public boolean onQueryTextChange(final String newText) {
                   search(newText);
                    update_Bottom();


                    return false;

                }
            });


        Button cancel = (Button) findViewById( R.id.cancel_btn_search);

        cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reBack();
                }
            });

    }

    private void search(String newText){

        //若搜索内容为空
        if(newText.isEmpty()){
            list_result.clear();
        }
        else{

            for (Note note : list_search) {//开始搜索

                //搜索内容搜索到相关
                if (note.getName().contains(newText) ) {

                    if(list_result.indexOf(note)==-1) {//且 结果集内不含有此内容
                        list_result.add(note);
                    }
                }else{
                    //搜索内容搜索不到相关 检测是否之前有加入结果集 有则删除
                    if(list_result.indexOf(note)!=-1) {
                        list_result.remove(note);
                    }
                }
            }
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case R.id.text1_history:
                searchView.setQuery(text1.getText().toString(),false);
                search(text1.getText().toString());
                break;
            case R.id.text2_history:
                searchView.setQuery(text2.getText().toString(),false);
                search(text2.getText().toString());
                break;
            case R.id.text3_history:
                searchView.setQuery(text3.getText().toString(),false);
                search(text3.getText().toString());
                break;


            case R.id.del1_history:
                visibleNumber--;
                hideView(delete1,text1);
                break;
            case R.id.del2_history:
                visibleNumber--;
                hideView(delete2,text2);
                break;
            case R.id.del3_history:
                visibleNumber--;
                hideView(delete3,text3);
                break;
            case R.id.clear_history:
                clearHistory();
                break;
            default:
                break;


        }

    }


    private void clearHistory(){
        text1.setText("");
        text2.setText("");
        text3.setText("");
        hideAllView();
    }


    private void getHistory() {


        SharedPreferences reader = getSharedPreferences("history", MODE_PRIVATE);

        String data = reader.getString("data_history", "");

        ArrayList<String> history = new ArrayList<>();


        String [] get=  data.split("\\|");

        for( String str : get){
            if(! history.contains( str )){
                history.add( str );
            }
        }


        int length = history.size();

        if (length>= 3) {
            text1.setText(history.get(0));
            text2.setText(history.get(1));
            text3.setText(history.get(2));
        }
        else if (length == 2) {
            text1.setText(history.get(0));
            text2.setText(history.get(1));
        }
        else if (length== 1) {
            text1.setText(history.get(0));
        }

        showOrHideView();
    }





    private void saveHistory(String s){


        StringBuilder data = new StringBuilder();

        int historyCount = 0 ;

        if(! StringUtil.isEmpty(s)) {
            data.append(s);
            data.append("|");
            historyCount++;
        }


        if (!StringUtil.isEmpty(text1.getText().toString())) {
            data.append(text1.getText().toString());
            data.append("|");
            historyCount++;
        }

        if (!StringUtil.isEmpty(text2.getText().toString()) ) {
            data.append(text2.getText().toString());
            data.append("|");
            historyCount++;
        }

        if (!StringUtil.isEmpty(text3.getText().toString()) && historyCount!=3) {
            data.append(text3.getText().toString());
            data.append("|");
        }

        SharedPreferences.Editor editor = getSharedPreferences("history",MODE_PRIVATE).edit();

        if(historyCount == 0){
            editor.putString("data_history","");
            editor.apply();
            return ;
        }

        editor.putString("data_history",data.toString());
        editor.apply();
    }



    private void showOrHideView(){

        int hideNumber = 0;

        if(text1.getText().toString().trim().equals("")){
            hideView(delete1,text1);
            hideNumber++;
        }else{
            showView(delete1,text1);
        }

        if(text2.getText().toString().trim().equals("")){
            hideView(delete2,text2);
            hideNumber++;
        }else{
            showView(delete2,text2);
        }

        if(text3.getText().toString().trim().equals("")){
            hideView(delete3,text3);
            hideNumber++;
        }else{
            showView(delete3,text3);
        }



        if(hideNumber == 3){
            clear.setVisibility(View.GONE);
        }else{
            clear.setVisibility(View.VISIBLE);
        }

        visibleNumber = 3 - hideNumber;
    }

    private void showView(TextView delete,Button text){
        delete.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
    }

    private void hideView(TextView delete,Button text){

        delete.setVisibility(View.GONE);
        text.setVisibility(View.GONE);
        text.setText("");

        if(visibleNumber == 0){
            clear.setVisibility(View.GONE);
        }
    }


    private void hideAllView(){

        clear.setVisibility(View.GONE);
        hideView(delete1,text1);
        hideView(delete2,text2);
        hideView(delete3,text3);

    }

    private void update_Bottom(){

        TextView bottom = (TextView) findViewById(R.id.bottom_search);

        if(searchView.getQuery().toString().trim().isEmpty()){
            gone_Bottom();
            return;
        }

        bottom.setVisibility(View.VISIBLE);
        bottom.setText("找到了 "+list_result.size() +" 条记录");

    }
    private void gone_Bottom(){

        TextView bottom = (TextView) findViewById(R.id.bottom_search);
        bottom.setVisibility(View.GONE);
    }

    private void clearSearchHint() {
        searchView.setQuery("", true);
    }



    private void hideOrOpenKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        switch(keyCode){
            case KeyEvent.KEYCODE_BACK:
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

}
