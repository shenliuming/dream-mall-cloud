package edu.user.cloud.dream.controller;

import edu.user.cloud.dream.common.TokenToAdminUser;
import edu.user.cloud.dream.entity.AdminUser;
import edu.user.cloud.dream.entity.AdminUserToken;
import edu.user.cloud.dream.entity.dto.AdminLoginReqDto;
import edu.user.cloud.dream.entity.dto.UpdateAdminNameReqDto;
import edu.user.cloud.dream.entity.dto.UpdateAdminPasswordReqDto;
import edu.user.cloud.dream.service.AdminUserService;
import edu.user.cloud.dream.util.Result;
import edu.user.cloud.dream.util.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Date: 2023-11-08 15:30
 * @Author： shenliuming
 * @return：
 */
@RestController
public class DreamMallCloudAdminUserController {
    private static final Logger logger = LoggerFactory.getLogger(DreamMallCloudAdminUserController.class);
    @Autowired
    private AdminUserService adminUserService;

    @GetMapping("/users/admin/test/{userId}")
    public String test(@PathVariable("userId") int userId) {
        String userName = "user:" + userId;
        // 返回信息给调用端
        return userName;
    }

    @RequestMapping(value = "/adminUser/login", method = RequestMethod.POST)
    public Result<String> login(@RequestBody @Valid AdminLoginReqDto adminLoginReqDto) {
        String loginResult = adminUserService.login(adminLoginReqDto.getUserName(), adminLoginReqDto.getPasswordMd5());
        logger.info("manage login api,adminName={},loginResult={}", adminLoginReqDto.getUserName(), loginResult);

        //登录成功
        if (!StringUtils.isEmpty(loginResult) && loginResult.length() == 32) {
            Result result = ResultGenerator.genSuccessResult();
            result.setData(loginResult);
            return result;
        }
        //登录失败
        return ResultGenerator.genFailResult(loginResult);
    }

    @RequestMapping(value = "/adminUser/profile", method = RequestMethod.POST)
    public Result profile(@TokenToAdminUser AdminUserToken adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        AdminUser adminUserEntity = adminUserService.getUserDetailById(adminUser.getAdminUserId());
        if (adminUserEntity != null) {
            adminUserEntity.setLoginPassword("******");
            Result result = ResultGenerator.genSuccessResult();
            result.setData(adminUserEntity);
            return result;
        }
        return ResultGenerator.genFailResult("无此用户数据");
    }

    @RequestMapping(value = "/adminUser/password", method = RequestMethod.PUT)
    public Result passwordUpdate(@RequestBody @Valid UpdateAdminPasswordReqDto adminPasswordReqDto, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        if (adminUserService.updatePassword(adminUser.getAdminUserId(), adminPasswordReqDto.getOriginalPassword(), adminPasswordReqDto.getNewPassword())) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("DB ERROR");
        }
    }

    @RequestMapping(value = "/adminUser/name", method = RequestMethod.PUT)
    public Result nameUpdate(@RequestBody @Valid UpdateAdminNameReqDto adminNameReqDto, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        if (adminUserService.updateName(adminUser.getAdminUserId(), adminNameReqDto.getLoginUserName(), adminNameReqDto.getNickName())) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("DB ERROR");
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    public Result logout(@TokenToAdminUser AdminUserToken adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        adminUserService.logout(adminUser.getAdminUserId());
        return ResultGenerator.genSuccessResult();
    }
}