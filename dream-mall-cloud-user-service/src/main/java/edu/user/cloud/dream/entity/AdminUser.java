package edu.user.cloud.dream.entity;

import lombok.Data;

/**
 * @Date: 2023-11-08 16:11
 * @Author： shenliuming
 * @return：
 */
@Data
public class AdminUser {
    private Long adminUserId;

    private String loginUserName;

    private String loginPassword;

    private String nickName;

    private Byte locked;
}
