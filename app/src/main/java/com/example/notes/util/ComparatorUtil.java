package com.example.notes.util;

import java.util.*;

/**
 * Created by 阿买 on 2017/3/26.
 */

public class ComparatorUtil implements Comparator<Note> {

    @Override
    public int compare(Note o1, Note o2) {

        return  o1.compareTo(o2);

    }

}
