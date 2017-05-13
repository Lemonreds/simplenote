package com.example.notes.Model;

import java.io.Serializable;

/**
 * Created by 阿买 on 2017/3/25.
 */

public class Item  implements Serializable {

    private String folderName;
    private String name;
    private Date date;


    public Item(String name,Date date,String folderName) {
        this.folderName=folderName;
        this.date = date;
        this.name = name;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
