package com.example.notes.Model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * 存储便签的时间
 */
public class Date implements Serializable{


    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    public Date() {

        Calendar cal = Calendar.getInstance();

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
    }



    public String getEasyDate() {

        StringBuilder reStr = new StringBuilder();

        DecimalFormat format = new DecimalFormat("00");

        Date nowTime = new Date();//获取最新的当前时间


        if( nowTime.getYear()==this.year){

            if( nowTime.getMonth() == this.month){

                if(nowTime.getDay() == this.day){
                        if(nowTime.getHour() == this.getHour()
                            && nowTime.getMinute() -this.getMinute() <=10) {
                            reStr.append("刚刚");
                        }else {
                            reStr.append(format.format(this.hour)+":"+format.format(this.minute));

                        }
                }else{
                        int distance = nowTime.getDay()-this.day;

                        if(distance>=2 && distance <=7) return this.getDayOfWeeK();

                        if(distance==1){
                            reStr.append("昨天");
                        }else{
                            reStr.append( format.format(this.month)+"/"+format.format(this.day));
                        }
                }

            }else{
                reStr.append( format.format(this.month)+"/"+format.format(this.day));
            }
        }
        else {
            reStr.append( format.format(this.month)+"/"+format.format(this.day));
        }

        return reStr.toString();

    }




    public  String getDetailDate(){

        StringBuilder reStr = new StringBuilder();

        DecimalFormat format = new DecimalFormat("00");


        reStr.append(this.year+"年"+format.format(this.month)+"月"+format.format(this.day)+"日");

        reStr.append(" "+format.format(this.hour)+":"+format.format(this.minute));

        return  reStr.toString();
    }



    @NonNull
    public String getDayOfWeeK(){

        int h,q,m,j,k,y;
        q=day;
        m=month;
        y=year;


        if (( month == 1)  || ( month == 2)) {
            month = 12+month ;
            --y;
        }

        j=Math.abs(y / 100);
        k=y%100;
        h =(q+ 26*(m+1)/10 + k + k/4 + j/4 + 5*j) % 7 ;



        StringBuilder reStr = new StringBuilder("星期");
        switch (h){
            case 1:
                reStr.append("天");
                break;
            case 2:
                reStr.append("一");
                break;
            case 3:
                reStr.append("二");
                break;
            case 4:
                reStr.append("三");
                break;
            case 5:
                reStr.append("四");
                break;
            case 6:
                reStr.append("五");
                break;
            case 0:
                reStr.append("六");
                break;
            default:
                break;
        }
        return reStr.toString();
    }


    public int getLeaveDay(Date date){

        java.util.Date d1 = new java.util.Date(year,month,day);
        java.util.Date d2 = new java.util.Date(date.getYear(),date.getMonth(),date.getDay());

        return (int)((d1.getTime() - d2.getTime())/86400000);
    }

    public int getDay() {
        return day;
    }

    public int getMinute() {
        return minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getDayString(){
        return  Integer.toString(day);
    }
    public String getMonthString(){
        return  Integer.toString(month);
    }
}
