package edu.user.cloud.dream.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
/**
 * @Date: 2023-11-08 16:37
 * @Author： shenliuming
 * @return：
 */
@Data
public class UpdateAdminNameReqDto {

    @NotEmpty(message = "loginUserName不能为空")
    private String loginUserName;

    @NotEmpty(message = "nickName不能为空")
    private String nickName;
}
