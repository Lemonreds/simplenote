package com.example.notes.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 阿买 on 2017/1/19.
 */

public class StringUtil {




    public static boolean isEmpty(String str){
        if(str==null || str.trim().equals("") )
            return true;
        else return false;
    }



    public static String clearHtml(String html) {


       // String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
        //String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
        String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符


        if(isEmpty(html)) return html;
/**
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(html);
        html = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(html);
        html = m_style.replaceAll(""); // 过滤style标签
**/
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
