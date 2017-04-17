package com.example.notes.Manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.notes.util.Date;
import com.example.notes.util.MsgToast;
import com.example.notes.util.Note;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by 阿买 on 2017/3/21.
 */

public class DBManager {

    private Context mContext;

    public DBManager(Context context){
        mContext=context;
    }


    public void insert(String folderName,Note note) {


        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(note);
            objectOutputStream.flush();

            byte []data = arrayOutputStream.toByteArray();

            objectOutputStream.close();
            arrayOutputStream.close();

            DBHelper dbHelper = DBHelper.getInstance(mContext);
            SQLiteDatabase database = dbHelper.getWritableDatabase();

            database.execSQL(
                    "insert into "+folderName+"(item) values(?)", new Object[] {data});
            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void delete(String folderName,Note note){



        SQLiteDatabase database = DBHelper.getInstance(mContext).getWritableDatabase();


        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(note);
            objectOutputStream.flush();

            byte [] data = arrayOutputStream.toByteArray();

            objectOutputStream.close();
            arrayOutputStream.close();
            database.execSQL("delete from "+ folderName + " where item = ?",new Object[] {data});

            if( !folderName.equals("recycle")){
                note.setDeleteDate(new Date());
                insert("recycle",(note));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        database.close();
    }


    public void moveToFolder(String toFolder,Note note){

        Note notClone = note.getClone();

        delete(note.getFolderName(),note);
        delete("recycle",note);


        notClone.setFolderName(toFolder);
        insert(toFolder,notClone);

    }

    public ArrayList<Note> search(String folderName) {


        ArrayList <Note> list = new ArrayList<>();

        Note note ;

        DBHelper dbHelper = DBHelper.getInstance(mContext);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from " + folderName, null);

        if (cursor != null) {

            while (cursor.moveToNext()) {
                byte data[] = cursor.getBlob(cursor.getColumnIndex("item"));
                ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(arrayInputStream);
                    note = (Note)inputStream.readObject();
                    list.add(note);
                    inputStream.close();
                    arrayInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            cursor.close();
        }

        return list;
    }

    public void upDate(String folderName,Note preNote,Note newNote){

        byte[] pre_data = getData(preNote);
        byte[] new_data = getData(newNote);

        SQLiteDatabase database = DBHelper.getInstance(mContext).getWritableDatabase();

        database.execSQL("update "+ folderName+ " set item = ? where item = ?",
                new Object[]{new_data,pre_data});

        database.close();
    }





    private byte[] getData(Note note){

        byte [] data = null;
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(note);
            objectOutputStream.flush();
            data = arrayOutputStream.toByteArray();
            objectOutputStream.close();
            arrayOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  data;
    }

    public ArrayList<String> getTableName(){

        ArrayList<String>  tableName = new ArrayList<>();

        DBHelper dbHelper = DBHelper.getInstance(mContext);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery
                ("select name from sqlite_master where type='table' order by name", null);

        while(cursor.moveToNext()){
            String name = cursor.getString(0);
            tableName.add(name);
        }
        //删除系统表
        tableName.remove("android_metadata");
        tableName.remove("sqlite_sequence");
        //回收站
        tableName.remove("recycle");

        cursor.close();
        return tableName;
    }

    public int getTableLength(String tableName){


        if(tableName.equals("最近删除")){
            tableName = "recycle";
        }

        DBHelper dbHelper = DBHelper.getInstance(mContext);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor=database.rawQuery("select count(*) from "+ tableName, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();

        return (int)count;
    }

    public void recovery(Note note){

        String belongFolder = note.getFolderName();

        DBHelper db = DBHelper.getInstance(mContext);

        if( ! db.tabbleIsExist(belongFolder)){
            db.add_table(belongFolder);
        }



        insert(belongFolder,note);

        delete("recycle",note);
    }

    public int clearAllFolder(String folderName){
        DBHelper dbHelper = DBHelper.getInstance(mContext);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        return  database.delete(folderName,null,null);
    }


}
