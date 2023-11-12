package edu.recommend.cloud.dream.controller;

import edu.common.cloud.dream.dto.base.BaseRespDto;
import edu.common.cloud.dream.dto.base.BaseRespDtoWrapper;
import edu.common.cloud.dream.dto.base.BatchIdReqDto;
import edu.common.cloud.dream.dto.base.PageQueryUtil;
import edu.common.cloud.dream.dto.recommend.IndexConfigInsertDto;
import edu.common.cloud.dream.dto.recommend.IndexConfigUpdDto;
import edu.common.cloud.dream.entity.annotations.TokenToAdminUser;
import edu.common.cloud.dream.entity.goods.LoginAdminUser;
import edu.common.cloud.dream.entity.recommend.IndexConfig;
import edu.common.cloud.dream.enums.IndexConfigTypeEnum;
import edu.common.cloud.dream.enums.ServiceResultEnum;
import edu.recommend.cloud.dream.service.DreamMallIndexConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date: 2023-11-11 14:38
 * @Author： shenliuming
 * @return：
 */
@RestController
@Api(value = "v1", tags = "后台管理系统首页配置模块接口")
@RequestMapping("/indexConfigs/admin")
public class DreamAdminIndexConfigController {

    private static final Logger logger = LoggerFactory.getLogger(DreamAdminIndexConfigController.class);

    @Autowired
    private DreamMallIndexConfigService dreamMallIndexConfigService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "首页配置列表", notes = "首页配置列表")
    public BaseRespDto list(@RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
                            @RequestParam(required = false) @ApiParam(value = "每页条数") Integer pageSize,
                            @RequestParam(required = false) @ApiParam(value = "1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐") Integer configType, @TokenToAdminUser LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        if (pageNumber == null || pageNumber < 1 || pageSize == null || pageSize < 10) {
            return BaseRespDtoWrapper.genFailBaseRespDto("分页参数异常！");
        }
        IndexConfigTypeEnum indexConfigTypeEnum = IndexConfigTypeEnum.getIndexConfigTypeEnumByType(configType);
        if (indexConfigTypeEnum.equals(IndexConfigTypeEnum.DEFAULT)) {
            return BaseRespDtoWrapper.genFailBaseRespDto("非法参数！");
        }
        Map params = new HashMap(8);
        params.put("page", pageNumber);
        params.put("limit", pageSize);
        params.put("configType", configType);
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return BaseRespDtoWrapper.genSuccessBaseRespDto(dreamMallIndexConfigService.getConfigsPage(pageUtil));
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增首页配置项", notes = "新增首页配置项")
    public BaseRespDto save(@RequestBody @Valid IndexConfigInsertDto indexConfigAddParam, @TokenToAdminUser LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        IndexConfig indexConfig = new IndexConfig();
        BeanUtils.copyProperties(indexConfigAddParam, indexConfig);
        String result = dreamMallIndexConfigService.saveIndexConfig(indexConfig);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return BaseRespDtoWrapper.genSuccessBaseRespDto();
        } else {
            return BaseRespDtoWrapper.genFailBaseRespDto(result);
        }
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiOperation(value = "修改首页配置项", notes = "修改首页配置项")
    public BaseRespDto update(@RequestBody @Valid IndexConfigUpdDto indexConfigEditParam, @TokenToAdminUser LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        IndexConfig indexConfig = new IndexConfig();
        BeanUtils.copyProperties(indexConfigEditParam, indexConfig);
        String result = dreamMallIndexConfigService.updateIndexConfig(indexConfig);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return BaseRespDtoWrapper.genSuccessBaseRespDto();
        } else {
            return BaseRespDtoWrapper.genFailBaseRespDto(result);
        }
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取单条首页配置项信息", notes = "根据id查询")
    public BaseRespDto info(@PathVariable("id") Long id, @TokenToAdminUser LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        IndexConfig config = dreamMallIndexConfigService.getIndexConfigById(id);
        if (config == null) {
            return BaseRespDtoWrapper.genFailBaseRespDto("未查询到数据");
        }
        return BaseRespDtoWrapper.genSuccessBaseRespDto(config);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/batchDelete", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量删除首页配置项信息", notes = "批量删除首页配置项信息")
    public BaseRespDto delete(@RequestBody BatchIdReqDto batchIdParam, @TokenToAdminUser LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        if (batchIdParam == null || batchIdParam.getIds().length < 1) {
            return BaseRespDtoWrapper.genFailBaseRespDto("参数异常！");
        }
        if (dreamMallIndexConfigService.deleteBatch(batchIdParam.getIds())) {
            return BaseRespDtoWrapper.genSuccessBaseRespDto();
        } else {
            return BaseRespDtoWrapper.genFailBaseRespDto("删除失败");
        }
    }




}
