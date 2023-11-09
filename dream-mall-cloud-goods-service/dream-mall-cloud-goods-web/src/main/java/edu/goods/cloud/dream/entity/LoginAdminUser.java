package edu.goods.cloud.dream.entity;

import lombok.Data;

/**
 * @Date: 2023-11-09 15:52
 * @Author： shenliuming
 * @return：
 */
@Data
public class LoginAdminUser {
    private Long adminUserId;

    private String loginUserName;

    private String loginPassword;

    private String nickName;

    private Byte locked;
}
