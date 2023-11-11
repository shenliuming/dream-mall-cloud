package edu.goods.cloud.dream.service;

import edu.common.cloud.dream.dto.base.PageQueryUtil;
import edu.common.cloud.dream.dto.base.PageResult;
import edu.common.cloud.dream.entity.goods.GoodsCategory;

import java.util.List;

/**
 * @Date: 2023-11-10 15:30
 * @Author： shenliuming
 * @return：
 */
public interface DreamMallGoodsCategoryService {

    String saveCategory(GoodsCategory goodsCategory);

    String updateGoodsCategory(GoodsCategory goodsCategory);

    GoodsCategory getGoodsCategoryById(Long id);

    Boolean deleteBatch(Long[] ids);

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getCategorisPage(PageQueryUtil pageUtil);

    /**
     * 根据parentId和level获取分类列表
     *
     * @param parentIds
     * @param categoryLevel
     * @return
     */
    List<GoodsCategory> selectByLevelAndParentIdsAndNumber(List<Long> parentIds, int categoryLevel);
}
