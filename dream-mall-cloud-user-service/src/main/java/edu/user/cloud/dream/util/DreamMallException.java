package edu.user.cloud.dream.util;

/**
 * @Date: 2023-11-08 16:24
 * @Author： shenliuming
 * @return：
 */
public class DreamMallException extends RuntimeException{
    public DreamMallException(){}
    public DreamMallException(String message){super(message);}
    public static void fail(String message) {
        throw new DreamMallException(message);
    }
}
