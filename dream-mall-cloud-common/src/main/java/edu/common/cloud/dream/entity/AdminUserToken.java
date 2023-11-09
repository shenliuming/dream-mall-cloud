package edu.common.cloud.dream.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Date: 2023-11-08 16:12
 * @Author： shenliuming
 * @return：
 */
@Data
public class AdminUserToken implements Serializable {
    private Long adminUserId;

    private String token;
}
