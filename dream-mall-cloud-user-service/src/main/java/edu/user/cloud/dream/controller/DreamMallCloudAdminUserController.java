package edu.user.cloud.dream.controller;

import edu.common.cloud.dream.dto.BaseRespDto;
import edu.common.cloud.dream.dto.BaseRespDtoWrapper;
import edu.user.cloud.dream.common.TokenToAdminUser;
import edu.user.cloud.dream.entity.AdminUser;
import edu.user.cloud.dream.entity.AdminUserToken;
import edu.user.cloud.dream.entity.dto.AdminLoginReqDto;
import edu.user.cloud.dream.entity.dto.UpdateAdminNameReqDto;
import edu.user.cloud.dream.entity.dto.UpdateAdminPasswordReqDto;
import edu.user.cloud.dream.service.AdminUserService;

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
    public BaseRespDto<String> login(@RequestBody @Valid AdminLoginReqDto adminLoginReqDto) {
        String loginBaseRespDto = adminUserService.login(adminLoginReqDto.getUserName(), adminLoginReqDto.getPasswordMd5());
        logger.info("manage login api,adminName={},loginBaseRespDto={}", adminLoginReqDto.getUserName(), loginBaseRespDto);

        //登录成功
        if (!StringUtils.isEmpty(loginBaseRespDto) && loginBaseRespDto.length() == 32) {
            BaseRespDto BaseRespDto = BaseRespDtoWrapper.genSuccessBaseRespDto();
            BaseRespDto.setData(loginBaseRespDto);
            return BaseRespDto;
        }
        //登录失败
        return BaseRespDtoWrapper.genFailBaseRespDto(loginBaseRespDto);
    }

    @RequestMapping(value = "/adminUser/profile", method = RequestMethod.POST)
    public BaseRespDto profile(@TokenToAdminUser AdminUserToken adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        AdminUser adminUserEntity = adminUserService.getUserDetailById(adminUser.getAdminUserId());
        if (adminUserEntity != null) {
            adminUserEntity.setLoginPassword("******");
            BaseRespDto BaseRespDto = BaseRespDtoWrapper.genSuccessBaseRespDto();
            BaseRespDto.setData(adminUserEntity);
            return BaseRespDto;
        }
        return BaseRespDtoWrapper.genFailBaseRespDto("无此用户数据");
    }

    @RequestMapping(value = "/adminUser/password", method = RequestMethod.PUT)
    public BaseRespDto passwordUpdate(@RequestBody @Valid UpdateAdminPasswordReqDto adminPasswordReqDto, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        if (adminUserService.updatePassword(adminUser.getAdminUserId(), adminPasswordReqDto.getOriginalPassword(), adminPasswordReqDto.getNewPassword())) {
            return BaseRespDtoWrapper.genSuccessBaseRespDto();
        } else {
            return BaseRespDtoWrapper.genFailBaseRespDto("DB ERROR");
        }
    }

    @RequestMapping(value = "/adminUser/name", method = RequestMethod.PUT)
    public BaseRespDto nameUpdate(@RequestBody @Valid UpdateAdminNameReqDto adminNameReqDto, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        if (adminUserService.updateName(adminUser.getAdminUserId(), adminNameReqDto.getLoginUserName(), adminNameReqDto.getNickName())) {
            return BaseRespDtoWrapper.genSuccessBaseRespDto();
        } else {
            return BaseRespDtoWrapper.genFailBaseRespDto("DB ERROR");
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    public BaseRespDto logout(@TokenToAdminUser AdminUserToken adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        adminUserService.logout(adminUser.getAdminUserId());
        return BaseRespDtoWrapper.genSuccessBaseRespDto();
    }
}