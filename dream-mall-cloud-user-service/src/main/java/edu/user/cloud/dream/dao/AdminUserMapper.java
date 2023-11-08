package edu.user.cloud.dream.dao;

import edu.user.cloud.dream.entity.AdminUser;
import org.apache.ibatis.annotations.ReqDto;

/**
 * @Date: 2023-11-08 16:41
 * @Author： shenliuming
 * @return：
 */
public interface AdminUserMapper {
    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser login(@ReqDto("userName") String userName, @ReqDto("password") String password);

    AdminUser selectByPrimaryKey(Long adminUserId);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);
}
