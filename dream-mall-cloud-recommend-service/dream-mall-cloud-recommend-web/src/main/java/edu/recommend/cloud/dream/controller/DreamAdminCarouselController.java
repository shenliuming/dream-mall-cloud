package edu.recommend.cloud.dream.controller;

import edu.common.cloud.dream.dto.base.BaseRespDto;
import edu.common.cloud.dream.dto.base.BaseRespDtoWrapper;
import edu.common.cloud.dream.dto.base.BatchIdReqDto;
import edu.common.cloud.dream.dto.base.PageQueryUtil;
import edu.common.cloud.dream.dto.recommend.CarouselInsertDto;
import edu.common.cloud.dream.dto.recommend.CarouselUpdDto;
import edu.common.cloud.dream.entity.annotations.TokenToAdminUser;
import edu.common.cloud.dream.entity.goods.LoginAdminUser;
import edu.common.cloud.dream.entity.recommend.Carousel;
import edu.common.cloud.dream.enums.ServiceResultEnum;
import edu.recommend.cloud.dream.service.DreamMallCarouselService;
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
 * @Date: 2023-11-11 14:25
 * @Author： shenliuming
 * @return：
 */
@RestController
@Api(value = "v1", tags = "后台管理系统轮播图模块接口")
@RequestMapping("/carousels/admin")
public class DreamAdminCarouselController {
    private static final Logger logger = LoggerFactory.getLogger(DreamAdminCarouselController.class);

    @Autowired
    private DreamMallCarouselService dreamMallCarouselService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "轮播图列表", notes = "轮播图列表")
    public BaseRespDto list(@RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
                            @RequestParam(required = false) @ApiParam(value = "每页条数") Integer pageSize, @TokenToAdminUser LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        if (pageNumber == null || pageNumber < 1 || pageSize == null || pageSize < 10) {
            return BaseRespDtoWrapper.genFailBaseRespDto("分页参数异常！");
        }
        Map params = new HashMap(4);
        params.put("page", pageNumber);
        params.put("limit", pageSize);
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return BaseRespDtoWrapper.genSuccessBaseRespDto(dreamMallCarouselService.getCarouselPage(pageUtil));
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增轮播图", notes = "新增轮播图")
    public BaseRespDto save(@RequestBody @Valid CarouselInsertDto carouselAddParam, @TokenToAdminUser LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        Carousel carousel = new Carousel();
        BeanUtils.copyProperties(carouselAddParam, carousel);
        String result = dreamMallCarouselService.saveCarousel(carousel);
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
    @ApiOperation(value = "修改轮播图信息", notes = "修改轮播图信息")
    public BaseRespDto update(@RequestBody CarouselUpdDto carouselEditParam, @TokenToAdminUser LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        Carousel carousel = new Carousel();
        BeanUtils.copyProperties(carouselEditParam, carousel);
        String result = dreamMallCarouselService.updateCarousel(carousel);
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
    @ApiOperation(value = "获取单条轮播图信息", notes = "根据id查询")
    public BaseRespDto info(@PathVariable("id") Integer id, @TokenToAdminUser LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        Carousel carousel = dreamMallCarouselService.getCarouselById(id);
        if (carousel == null) {
            return BaseRespDtoWrapper.genFailBaseRespDto(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return BaseRespDtoWrapper.genSuccessBaseRespDto(carousel);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/batchDelete", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量删除轮播图信息", notes = "批量删除轮播图信息")
    public BaseRespDto delete(@RequestBody BatchIdReqDto batchIdParam, @TokenToAdminUser LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        if (batchIdParam == null || batchIdParam.getIds().length < 1) {
            return BaseRespDtoWrapper.genFailBaseRespDto("参数异常！");
        }
        if (dreamMallCarouselService.deleteBatch(batchIdParam.getIds())) {
            return BaseRespDtoWrapper.genSuccessBaseRespDto();
        } else {
            return BaseRespDtoWrapper.genFailBaseRespDto("删除失败");
        }
    }
}
