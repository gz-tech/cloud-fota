package com.upuphone.cloudplatform.fota;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author guangzheng.ding
 * @date 2021/12/7 16:00
 */
public class UrlCodeUtil {

    public static String getURLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static  String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = URLEncoder.encode(str, "a");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
