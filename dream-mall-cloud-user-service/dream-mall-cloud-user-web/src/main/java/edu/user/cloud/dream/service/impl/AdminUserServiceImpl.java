package edu.user.cloud.dream.service.impl;

import edu.common.cloud.dream.util.NumberUtil;
import edu.common.cloud.dream.util.SystemUtil;
import edu.user.cloud.dream.dao.AdminUserMapper;
import edu.user.cloud.dream.dao.AdminUserTokenMapper;
import edu.user.cloud.dream.entity.AdminUser;
import edu.common.cloud.dream.entity.AdminUserToken;
import edu.user.cloud.dream.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Date: 2023-11-08 16:31
 * @Author： shenliuming
 * @return：
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private AdminUserTokenMapper adminUserTokenMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String login(String userName, String password) {
        AdminUser loginAdminUser = adminUserMapper.login(userName, password);
        if (loginAdminUser != null) {
            //登录后即执行修改token的操作
            String token = getNewToken(System.currentTimeMillis() + "", loginAdminUser.getAdminUserId());
            AdminUserToken adminUserToken = new AdminUserToken();
            adminUserToken.setAdminUserId(loginAdminUser.getAdminUserId());
            adminUserToken.setToken(token);
            ValueOperations<String, AdminUserToken> setToken = redisTemplate.opsForValue();
            setToken.set(token, adminUserToken, 2 * 24 * 60 * 60, TimeUnit.SECONDS); //过期时间 48 小时
            return token;
        }
        return "登录失败";
    }


    /**
     * 获取token值
     *
     * @param timeStr
     * @param userId
     * @return
     */
    private String getNewToken(String timeStr, Long userId) {
        String src = timeStr + userId + NumberUtil.genRandomNum(6);
        return SystemUtil.genToken(src);
    }


    @Override
    public AdminUser getUserDetailById(Long loginUserId) {
        return adminUserMapper.selectByPrimaryKey(loginUserId);
    }

    @Override
    public AdminUser getUserDetailByToken(String token) {
        ValueOperations<String, AdminUserToken> opsForAdminUserToken = redisTemplate.opsForValue();
        AdminUserToken adminUserToken = opsForAdminUserToken.get(token);
        if (adminUserToken != null) {
            return adminUserMapper.selectByPrimaryKey(adminUserToken.getAdminUserId());
        }
        return null;
    }

    @Override
    public Boolean updatePassword(Long loginUserId, String originalPassword, String newPassword) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        //当前用户非空才可以进行更改
        if (adminUser != null) {
            //比较原密码是否正确
            if (originalPassword.equals(adminUser.getLoginPassword())) {
                //设置新密码并修改
                adminUser.setLoginPassword(newPassword);
                if (adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0 && adminUserTokenMapper.deleteByPrimaryKey(loginUserId) > 0) {
                    //修改成功且清空当前token则返回true
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean updateName(Long loginUserId, String loginUserName, String nickName) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        //当前用户非空才可以进行更改
        if (adminUser != null) {
            //设置新名称并修改
            adminUser.setLoginUserName(loginUserName);
            adminUser.setNickName(nickName);
            if (adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0) {
                //修改成功则返回true
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean logout(Long adminUserId) {
        return adminUserTokenMapper.deleteByPrimaryKey(adminUserId) > 0;
    }

}
