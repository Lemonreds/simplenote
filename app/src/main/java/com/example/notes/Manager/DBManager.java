package com.example.notes.Manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.notes.Model.Date;
import com.example.notes.Model.Note;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * 数据库管理类
 */

public class DBManager {

    private Context mContext;

    public DBManager(Context context){
        mContext=context;
    }

    /**
     * 插入一个Note
     * @param folderName
     * @param note
     */
    public void insert(String folderName,Note note) {

        //对象转换成用二进制数组处理
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

    /**
     * 删除一个Note
     * @param folderName
     * @param note
     */

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
                Note note1 = note.getClone();
                //设置删除日期
                note1.setDeleteDate(new Date());
                insert("recycle",(note1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        database.close();
    }

    /**
     * 移动到其他文件夹
     * @param toFolder
     * @param note
     */
    public void moveToFolder(String toFolder,Note note){

        Note notClone = note.getClone();

        delete(note.getFolderName(),note);
        delete("recycle",note);


        notClone.setFolderName(toFolder);
        insert(toFolder,notClone);

    }

    /**
     * 查询某一文件夹下的Note
     * @param folderName
     * @return
     */
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

    /**
     * 更新Note
     * @param folderName
     * @param preNote
     * @param newNote
     */
    public void upDate(String folderName,Note preNote,Note newNote){

        byte[] pre_data = getData(preNote);
        byte[] new_data = getData(newNote);

        SQLiteDatabase database = DBHelper.getInstance(mContext).getWritableDatabase();

        database.execSQL("update "+ folderName+ " set item = ? where item = ?",
                new Object[]{new_data,pre_data});

        database.close();
    }


    /**
     * Note转化为二进制数组
     * @param note
     * @return
     */
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

    /**
     * 获取表名
     * @return
     */
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

    /**
     * 获取表的长度
     * @param tableName
     * @return
     */
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

    /**
     * 恢复
     * @param note
     */
    public void recovery(Note note){

        String belongFolder = note.getFolderName();

        DBHelper db = DBHelper.getInstance(mContext);

        if( ! db.folderIsExist(belongFolder)){
            db.add_table(belongFolder);
        }

        insert(belongFolder,note);

        delete("recycle",note);
    }

    /**
     * 清空文件夹
     * @param folderName
     * @return
     */
    public int clearAllFolder(String folderName){
        DBHelper dbHelper = DBHelper.getInstance(mContext);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        return  database.delete(folderName,null,null);
    }


}
