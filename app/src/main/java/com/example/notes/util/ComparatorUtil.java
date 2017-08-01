package com.example.notes.Util;

import com.example.notes.Model.Note;

import java.util.*;

/**
 * 比较工具类
 * 排序
 */
public class ComparatorUtil implements Comparator<Note> {

    @Override
    public int compare(Note o1, Note o2) {
        return  o1.compareTo(o2);
    }

}
