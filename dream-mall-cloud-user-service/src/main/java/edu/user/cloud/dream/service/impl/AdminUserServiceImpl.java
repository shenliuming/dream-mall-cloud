package edu.user.cloud.dream.service.impl;

import edu.user.cloud.dream.entity.AdminUser;
import edu.user.cloud.dream.service.AdminUserService;

/**
 * @Date: 2023-11-08 16:31
 * @Author： shenliuming
 * @return：
 */
public class AdminUserServiceImpl implements AdminUserService {

    @Override
    public String login(String userName, String password) {
        return null;
    }

    @Override
    public AdminUser getUserDetailById(Long loginUserId) {
        return null;
    }

    @Override
    public Boolean updatePassword(Long loginUserId, String originalPassword, String newPassword) {
        return null;
    }

    @Override
    public Boolean updateName(Long loginUserId, String loginUserName, String nickName) {
        return null;
    }

    @Override
    public Boolean logout(Long adminUserId) {
        return null;
    }
}
