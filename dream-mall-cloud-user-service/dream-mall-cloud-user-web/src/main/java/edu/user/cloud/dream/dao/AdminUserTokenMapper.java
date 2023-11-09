package edu.user.cloud.dream.dao;

import edu.common.cloud.dream.entity.AdminUserToken;

/**
 * @Date: 2023-11-08 16:37
 * @Author： shenliuming
 * @return：
 */
public interface AdminUserTokenMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(AdminUserToken record);

    int insertSelective(AdminUserToken record);

    AdminUserToken selectByPrimaryKey(Long userId);

    AdminUserToken selectByToken(String token);

    int updateByPrimaryKeySelective(AdminUserToken record);

    int updateByPrimaryKey(AdminUserToken record);

}
