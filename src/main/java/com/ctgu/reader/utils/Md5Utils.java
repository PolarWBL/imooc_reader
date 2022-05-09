package com.ctgu.reader.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Boliang Weng
 */
public class Md5Utils {
    public static String getMd5Digest(String string, int salt){
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] + salt);
        }
        String s = new String(chars);
        return DigestUtils.md5Hex(s);
    }
}
