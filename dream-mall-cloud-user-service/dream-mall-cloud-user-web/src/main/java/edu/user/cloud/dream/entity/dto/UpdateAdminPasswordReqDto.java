package edu.user.cloud.dream.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
/**
 * @Date: 2023-11-08 16:37
 * @Author： shenliuming
 * @return：
 */
@Data
public class UpdateAdminPasswordReqDto {

    @NotEmpty(message = "originalPassword不能为空")
    private String originalPassword;

    @NotEmpty(message = "newPassword不能为空")
    private String newPassword;
}
