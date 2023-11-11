package edu.goods.cloud.dream.service;

import edu.common.cloud.dream.dto.base.PageQueryUtil;
import edu.common.cloud.dream.dto.base.PageResult;
import edu.common.cloud.dream.entity.goods.Goods;

import java.util.List;

/**
 * @Date: 2023-11-10 15:30
 * @Author： shenliuming
 * @return：
 */
public interface DreamMallGoodsService {

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getGoodsPage(PageQueryUtil pageUtil);

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    String saveGoods(Goods goods);

    /**
     * 批量新增商品数据
     *
     * @param GoodsList
     * @return
     */
    void batchSaveGoods(List<Goods> GoodsList);

    /**
     * 修改商品信息
     *
     * @param goods
     * @return
     */
    String updateGoods(Goods goods);

    /**
     * 批量修改销售状态(上架下架)
     *
     * @param ids
     * @return
     */
    Boolean batchUpdateSellStatus(Long[] ids, int sellStatus);

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    Goods getGoodsById(Long id);
}
