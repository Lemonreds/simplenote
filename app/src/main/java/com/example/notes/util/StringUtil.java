package com.example.notes.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {




    public static boolean isEmpty(String str){
        if(str==null || str.trim().equals("") )
            return true;
        else return false;
    }



    public static String clearHtml(String html) {

        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
        String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符


        if(isEmpty(html)) return html;

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(html);
        html = m_html.replaceAll(""); // 过滤html标签

        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(html);
        html = m_space.replaceAll(""); // 过滤空格回车标签

        html = html.replaceAll("&nbsp;","");//过滤复选框


        return html.trim(); // 返回文本字符串
    }
}
