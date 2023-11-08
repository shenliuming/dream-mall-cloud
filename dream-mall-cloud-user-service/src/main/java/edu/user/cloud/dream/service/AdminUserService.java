package edu.user.cloud.dream.service;

import edu.user.cloud.dream.entity.AdminUser;

/**
 * @Date: 2023-11-08 16:29
 * @Author： shenliuming
 * @return：
 */
public interface AdminUserService {
    String login(String userName, String password);
    AdminUser getUserDetailById(Long loginUserId);
    Boolean updatePassword(Long loginUserId, String originalPassword, String newPassword);
    Boolean updateName(Long loginUserId, String loginUserName, String nickName);
    Boolean logout(Long adminUserId);
}
