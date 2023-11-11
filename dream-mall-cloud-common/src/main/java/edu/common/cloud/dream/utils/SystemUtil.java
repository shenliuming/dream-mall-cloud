package edu.common.cloud.dream.utils;

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
            String BaseRespDto = new BigInteger(1, md.digest()).toString(16);
            if (BaseRespDto.length() == 31) {
                BaseRespDto = BaseRespDto + "-";
            }
            System.out.println(BaseRespDto);
            return BaseRespDto;
        } catch (Exception e) {
            return null;
        }
    }
}
