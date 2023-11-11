package edu.goods.cloud.dream.controller;

import edu.common.cloud.dream.dto.base.BaseRespDto;
import edu.common.cloud.dream.dto.base.BaseRespDtoWrapper;
import edu.common.cloud.dream.dto.base.PageQueryUtil;
import edu.common.cloud.dream.dto.goods.BatchIdReqDto;
import edu.common.cloud.dream.dto.goods.GoodsCategoryInsertReqDto;
import edu.common.cloud.dream.dto.goods.GoodsCategoryUpdReqDto;
import edu.common.cloud.dream.entity.goods.GoodsCategory;
import edu.common.cloud.dream.enums.DreamMallCategoryLevelEnum;
import edu.common.cloud.dream.enums.ServiceResultEnum;
import edu.common.cloud.dream.entity.goods.LoginAdminUser;
import edu.goods.cloud.dream.service.DreamMallGoodsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date: 2023-11-09 15:57
 * @Author： shenliuming
 * @return：
 */
@RestController
@Api(value = "v1", tags = "后台管理系统-商品分类服务接口")
@RequestMapping("/categories/admin")
public class DreamAdminGoodsCategoryController {

    private static final Logger logger = LoggerFactory.getLogger(DreamAdminGoodsCategoryController.class);

    @Autowired
    private DreamMallGoodsCategoryService dreamMallGoodsCategoryService;


    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "商品分类列表", notes = "根据级别和上级分类id查询")
    public BaseRespDto list(@RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
                       @RequestParam(required = false) @ApiParam(value = "每页条数") Integer pageSize,
                       @RequestParam(required = false) @ApiParam(value = "分类级别") Integer categoryLevel,
                       @RequestParam(required = false) @ApiParam(value = "上级分类的id") Long parentId, LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        if (pageNumber == null || pageNumber < 1 || pageSize == null || pageSize < 10 || categoryLevel == null || categoryLevel < 0 || categoryLevel > 3 || parentId == null || parentId < 0) {
            return BaseRespDtoWrapper.genFailBaseRespDto("分页参数异常！");
        }
        Map params = new HashMap(8);
        params.put("page", pageNumber);
        params.put("limit", pageSize);
        params.put("categoryLevel", categoryLevel);
        params.put("parentId", parentId);
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return BaseRespDtoWrapper.genSuccessBaseRespDto(dreamMallGoodsCategoryService.getCategorisPage(pageUtil));
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list4Select", method = RequestMethod.GET)
    @ApiOperation(value = "商品分类列表", notes = "用于三级分类联动效果制作")
    public BaseRespDto listForSelect(@RequestParam("categoryId") Long categoryId, LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        if (categoryId == null || categoryId < 1) {
            return BaseRespDtoWrapper.genFailBaseRespDto("缺少参数！");
        }
        GoodsCategory category = dreamMallGoodsCategoryService.getGoodsCategoryById(categoryId);
        //既不是一级分类也不是二级分类则为不返回数据
        if (category == null || category.getCategoryLevel() == DreamMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return BaseRespDtoWrapper.genFailBaseRespDto("参数异常！");
        }
        Map categoryResult = new HashMap(4);
        if (category.getCategoryLevel() == DreamMallCategoryLevelEnum.LEVEL_ONE.getLevel()) {
            //如果是一级分类则返回当前一级分类下的所有二级分类，以及二级分类列表中第一条数据下的所有三级分类列表
            //查询一级分类列表中第一个实体的所有二级分类
            List<GoodsCategory> secondLevelCategories = dreamMallGoodsCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(categoryId), DreamMallCategoryLevelEnum.LEVEL_TWO.getLevel());
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                //查询二级分类列表中第一个实体的所有三级分类
                List<GoodsCategory> thirdLevelCategories = dreamMallGoodsCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), DreamMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                categoryResult.put("secondLevelCategories", secondLevelCategories);
                categoryResult.put("thirdLevelCategories", thirdLevelCategories);
            }
        }
        if (category.getCategoryLevel() == DreamMallCategoryLevelEnum.LEVEL_TWO.getLevel()) {
            //如果是二级分类则返回当前分类下的所有三级分类列表
            List<GoodsCategory> thirdLevelCategories = dreamMallGoodsCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(categoryId), DreamMallCategoryLevelEnum.LEVEL_THREE.getLevel());
            categoryResult.put("thirdLevelCategories", thirdLevelCategories);
        }
        return BaseRespDtoWrapper.genSuccessBaseRespDto(categoryResult);
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增分类", notes = "新增分类")
    public BaseRespDto save(@RequestBody @Valid GoodsCategoryInsertReqDto goodsCategoryAddParam, LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        GoodsCategory goodsCategory = new GoodsCategory();
        BeanUtils.copyProperties(goodsCategoryAddParam, goodsCategory);
        String result = dreamMallGoodsCategoryService.saveCategory(goodsCategory);

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
    @ApiOperation(value = "修改分类信息", notes = "修改分类信息")
    public BaseRespDto update(@RequestBody @Valid GoodsCategoryUpdReqDto goodsCategoryEditParam, LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        GoodsCategory goodsCategory = new GoodsCategory();
        BeanUtils.copyProperties(goodsCategoryEditParam, goodsCategory);
        String result = dreamMallGoodsCategoryService.updateGoodsCategory(goodsCategory);
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
    @ApiOperation(value = "获取单条分类信息", notes = "根据id查询")
    public BaseRespDto info(@PathVariable("id") Long id, LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        GoodsCategory goodsCategory = dreamMallGoodsCategoryService.getGoodsCategoryById(id);
        if (goodsCategory == null) {
            return BaseRespDtoWrapper.genFailBaseRespDto("未查询到数据");
        }
        return BaseRespDtoWrapper.genSuccessBaseRespDto(goodsCategory);
    }

    /**
     * 分类删除
     */
    @RequestMapping(value = "/batchDelete", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量删除分类信息", notes = "批量删除分类信息")
    public BaseRespDto delete(@RequestBody BatchIdReqDto batchIdParam, LoginAdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        if (batchIdParam == null || batchIdParam.getIds().length < 1) {
            return BaseRespDtoWrapper.genFailBaseRespDto("参数异常！");
        }
        if (dreamMallGoodsCategoryService.deleteBatch(batchIdParam.getIds())) {
            return BaseRespDtoWrapper.genSuccessBaseRespDto();
        } else {
            return BaseRespDtoWrapper.genFailBaseRespDto("删除失败");
        }
    }


}
