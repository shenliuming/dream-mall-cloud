package edu.goods.cloud.dream.controller;

import edu.common.cloud.dream.dto.base.BaseRespDto;
import edu.common.cloud.dream.dto.base.BaseRespDtoWrapper;
import edu.common.cloud.dream.dto.base.BatchIdReqDto;
import edu.common.cloud.dream.dto.base.PageQueryUtil;
import edu.common.cloud.dream.dto.goods.GoodsInsertReqDto;
import edu.common.cloud.dream.dto.goods.GoodsUpdReqDto;
import edu.common.cloud.dream.entity.annotations.TokenToAdminUser;
import edu.common.cloud.dream.entity.goods.Goods;
import edu.common.cloud.dream.entity.goods.GoodsCategory;
import edu.common.cloud.dream.entity.goods.LoginAdminUser;
import edu.common.cloud.dream.enums.ServiceResultEnum;
import edu.goods.cloud.dream.service.DreamMallGoodsCategoryService;
import edu.goods.cloud.dream.service.DreamMallGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date: 2023-11-10 14:22
 * @Author： shenliuming
 * @return：
 */
@RestController
@Api(value = "v1", tags = "后台管理系统商品模块接口")
@RequestMapping("/goods/admin")
public class DreamAdminGoodsInfoController {
    private static final Logger logger = LoggerFactory.getLogger(DreamAdminGoodsInfoController.class);

    @Autowired
    private DreamMallGoodsCategoryService goodsCategoryService;

    @Autowired
    private DreamMallGoodsService goodsService;


    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "商品列表", notes = "可根据名称和上架状态筛选")
    public BaseRespDto list(@RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
                       @RequestParam(required = false) @ApiParam(value = "每页条数") Integer pageSize,
                       @RequestParam(required = false) @ApiParam(value = "商品名称") String goodsName,
                       @RequestParam(required = false) @ApiParam(value = "上架状态 0-上架 1-下架") Integer goodsSellStatus, @TokenToAdminUser LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        if (pageNumber == null || pageNumber < 1 || pageSize == null || pageSize < 10) {
            return BaseRespDtoWrapper.genFailBaseRespDto("分页参数异常！");
        }
        Map params = new HashMap(8);
        params.put("page", pageNumber);
        params.put("limit", pageSize);
        if (!StringUtils.isEmpty(goodsName)) {
            params.put("goodsName", goodsName);
        }
        if (goodsSellStatus != null) {
            params.put("goodsSellStatus", goodsSellStatus);
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return BaseRespDtoWrapper.genSuccessBaseRespDto(goodsService.getGoodsPage(pageUtil));
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增商品信息", notes = "新增商品信息")
    public BaseRespDto save(@RequestBody @Valid GoodsInsertReqDto goodsAddParam, @TokenToAdminUser LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsAddParam, goods);
        String result = goodsService.saveGoods(goods);
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
    @ApiOperation(value = "修改商品信息", notes = "修改商品信息")
    public BaseRespDto update(@RequestBody @Valid GoodsUpdReqDto goodsEditParam, @TokenToAdminUser LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsEditParam, goods);
        String result = goodsService.updateGoods(goods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return BaseRespDtoWrapper.genSuccessBaseRespDto();
        } else {
            return BaseRespDtoWrapper.genFailBaseRespDto(result);
        }
    }

    /**
     * 详情
     */
    @GetMapping("/detail/{id}")
    @ApiOperation(value = "获取单条商品信息", notes = "根据id查询")
    public BaseRespDto info(@PathVariable("id") Long id, @TokenToAdminUser LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        Map goodsInfo = new HashMap(8);
        Goods goods = goodsService.getGoodsById(id);
        if (goods == null) {
            return BaseRespDtoWrapper.genFailBaseRespDto(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        goodsInfo.put("goods", goods);
        GoodsCategory thirdCategory;
        GoodsCategory secondCategory;
        GoodsCategory firstCategory;
        thirdCategory = goodsCategoryService.getGoodsCategoryById(goods.getGoodsCategoryId());
        if (thirdCategory != null) {
            goodsInfo.put("thirdCategory", thirdCategory);
            secondCategory = goodsCategoryService.getGoodsCategoryById(thirdCategory.getParentId());
            if (secondCategory != null) {
                goodsInfo.put("secondCategory", secondCategory);
                firstCategory = goodsCategoryService.getGoodsCategoryById(secondCategory.getParentId());
                if (firstCategory != null) {
                    goodsInfo.put("firstCategory", firstCategory);
                }
            }
        }
        return BaseRespDtoWrapper.genSuccessBaseRespDto(goodsInfo);
    }

    /**
     * 批量修改销售状态
     */
    @RequestMapping(value = "/updateStatus/{sellStatus}", method = RequestMethod.PUT)
    @ApiOperation(value = "批量修改销售状态", notes = "批量修改销售状态")
    public BaseRespDto delete(@RequestBody BatchIdReqDto batchIdParam, @PathVariable("sellStatus") int sellStatus, @TokenToAdminUser LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        if (batchIdParam == null || batchIdParam.getIds().length < 1) {
            return BaseRespDtoWrapper.genFailBaseRespDto("参数异常！");
        }
        if (sellStatus != 0 && sellStatus != 1) {
            return BaseRespDtoWrapper.genFailBaseRespDto("状态异常！");
        }
        if (goodsService.batchUpdateSellStatus(batchIdParam.getIds(), sellStatus)) {
            return BaseRespDtoWrapper.genSuccessBaseRespDto();
        } else {
            return BaseRespDtoWrapper.genFailBaseRespDto("修改失败");
        }
    }

    /**
     * 详情
     */
    @GetMapping("/goodsDetail")
    @ApiOperation(value = "获取单条商品信息", notes = "根据id查询")
    public BaseRespDto goodsDetail(@RequestParam("goodsId") Long goodsId) {
        Goods goods = goodsService.getGoodsById(goodsId);
        return BaseRespDtoWrapper.genSuccessBaseRespDto(goods);
    }


}
