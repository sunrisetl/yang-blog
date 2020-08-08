package net.chen.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Chen
 * 2020/8/2 20:01
 */
public class StringUtils {
    public static  final String regEx = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static boolean isEmpty(String text){
        return text == null || text.length() == 0;
    }

    /** 邮箱地址校验*/
    public static boolean isEmailAddress(String emailAddress){
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(emailAddress);
        return matcher.matches();
    }
}
