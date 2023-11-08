package edu.user.cloud.dream.util;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @Date: 2023-11-08 16:14
 * @Author： shenliuming
 * @return：
 */
public class SystemUtil {
    public static String genToken(String src) {
        if (null == src || "".equals(src)) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(src.getBytes());
            String result = new BigInteger(1, md.digest()).toString(16);
            if (result.length() == 31) {
                result = result + "-";
            }
            System.out.println(result);
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
