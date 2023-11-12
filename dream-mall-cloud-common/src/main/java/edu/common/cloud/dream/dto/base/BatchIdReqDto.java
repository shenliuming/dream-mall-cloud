package edu.common.cloud.dream.dto.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @Date: 2023-11-10 14:50
 * @Author： shenliuming
 * @return：
 */
@Data
public class BatchIdReqDto implements Serializable {
    Long[] ids;
}
