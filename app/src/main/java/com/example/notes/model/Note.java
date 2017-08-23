package com.example.notes.Model;

import com.example.notes.Util.StringUtil;

import java.io.Serializable;

/**
 * Created by 阿买 on 2017/1/18.
 */

public class Note extends Item implements Serializable ,Comparable{


    public static final  int RED_LEVEL = 2;
    public static final  int ORA_LEVEL = 1;
    public static final  int GRE_LEVEL = 0;


    private String text;

    private String location;

    private Date deleteDate;

    private int level;



    public Note(String name,Date date,String folderName) {
        super(name,date,folderName);
        this.location="";
        this.text = "";
        this.level  = GRE_LEVEL;
    }

    public Note(String name, Date date,
                String location ,String text,
                String folderName) {

        super(name, date, folderName);
        this.location = location;
        this.text = text;
    }


    public Note(String name, Date date,
                String location, String text,
                String folderName,
                int level) {
        super(name, date, folderName);
        this.level = level;
        this.location = location;
        this.text = text;
    }

    public Note getClone(){

        return  new Note(getName(),getDate(),getLocation(),getText(),getFolderName(),getLevel());

    }

    @Override
    public int compareTo(Object o) {

        java.util.Date d1 = new java.util.Date(getDate().getYear(),
                getDate().getMonth(),getDate().getDay(),getDate().getHour(),getDate().getMinute());

        Note o2 = (Note) o;
        java.util.Date d2 = new java.util.Date(o2.getDate().getYear(),
                o2.getDate().getMonth(),o2.getDate().getDay(),o2.getDate().getHour(),o2.getDate().getMinute());


        if(d1.before(d2))
            return  -1;
        else return 1;

    }


    public Date getDate() {
        return super.getDate();
    }

    public void setDate(Date date) {
        super.setDate(date);
    }

    public String getName() {
        return StringUtil.clearEnter(super.getName());
    }

    public void setName(String name) {
        super.setName(name);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public void setDeleteDate(Date delteDate) {
        this.deleteDate = delteDate;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


}
