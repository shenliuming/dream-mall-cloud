package edu.goods.cloud.dream.controller;

import edu.common.cloud.dream.dto.BaseRespDto;
import edu.common.cloud.dream.dto.BaseRespDtoWrapper;
import edu.goods.cloud.dream.common.TokenToAdminUser;
import edu.goods.cloud.dream.entity.LoginAdminUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date: 2023-11-09 15:57
 * @Author： shenliuming
 * @return：
 */
@RestController
@Api(value = "v1", tags = "后台管理系统分类模块接口")
@RequestMapping("/goods/admin")
public class DreamAdminGoodsCategoryController {

    private static final Logger logger = LoggerFactory.getLogger(DreamAdminGoodsCategoryController.class);

    @RequestMapping(value = "/testLoginAdminUser", method = RequestMethod.GET)
    @ApiOperation(value = "测试", notes = "测试")
    public BaseRespDto testLoginAdminUser(@TokenToAdminUser LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        return BaseRespDtoWrapper.genSuccessBaseRespDto();
    }

}
