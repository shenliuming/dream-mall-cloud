package edu.common.cloud.dream.utils;

import java.security.MessageDigest;

/**
 * @Date: 2023-11-08 16:26
 * @Author： shenliuming
 * @return：
 */
public class MD5Util {

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer BaseRespDtoSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            BaseRespDtoSb.append(byteToHexString(b[i]));

        return BaseRespDtoSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin, String charsetname) {
        String BaseRespDtoString = null;
        try {
            BaseRespDtoString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                BaseRespDtoString = byteArrayToHexString(md.digest(BaseRespDtoString
                        .getBytes()));
            else
                BaseRespDtoString = byteArrayToHexString(md.digest(BaseRespDtoString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return BaseRespDtoString;
    }

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
}
