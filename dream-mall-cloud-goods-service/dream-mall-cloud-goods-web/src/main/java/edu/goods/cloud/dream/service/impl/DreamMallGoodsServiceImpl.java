package edu.goods.cloud.dream.service.impl;

import edu.common.cloud.dream.dto.base.BaseRespDtoWrapper;
import edu.common.cloud.dream.dto.base.PageQueryUtil;
import edu.common.cloud.dream.dto.base.PageResult;
import edu.common.cloud.dream.entity.goods.Goods;
import edu.common.cloud.dream.entity.goods.GoodsCategory;
import edu.common.cloud.dream.enums.DreamMallCategoryLevelEnum;
import edu.common.cloud.dream.enums.ServiceResultEnum;
import edu.common.cloud.dream.exception.DreamMallException;
import edu.goods.cloud.dream.dao.DreamMallGoodsCategoryMapper;
import edu.goods.cloud.dream.dao.DreamMallGoodsMapper;
import edu.goods.cloud.dream.service.DreamMallGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @Date: 2023-11-10 15:30
 * @Author： shenliuming
 * @return：
 */
@Service
public class DreamMallGoodsServiceImpl implements DreamMallGoodsService {
    
    @Autowired
    private DreamMallGoodsMapper mallGoodsMapper;
    
    @Autowired
    private DreamMallGoodsCategoryMapper mallGoodsCategoryMapper;


    @Override
    public PageResult getGoodsPage(PageQueryUtil pageUtil) {
        List<Goods> goodsList = mallGoodsMapper.findGoodsList(pageUtil);
        int total = mallGoodsMapper.getTotalGoods(pageUtil);
        PageResult pageResult = new PageResult(goodsList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveGoods(Goods goods) {
        GoodsCategory goodsCategory = mallGoodsCategoryMapper.selectByPrimaryKey(goods.getGoodsCategoryId());
        // 分类不存在或者不是三级分类，则该参数字段异常
        if (goodsCategory == null || goodsCategory.getCategoryLevel().intValue() != DreamMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult();
        }
        if (mallGoodsMapper.selectByCategoryIdAndName(goods.getGoodsName(), goods.getGoodsCategoryId()) != null) {
            return ServiceResultEnum.SAME_GOODS_EXIST.getResult();
        }
        if (mallGoodsMapper.insertSelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public void batchSaveGoods(List<Goods> GoodsList) {
        if (!CollectionUtils.isEmpty(GoodsList)) {
            mallGoodsMapper.batchInsert(GoodsList);
        }
    }

    @Override
    public String updateGoods(Goods goods) {
        GoodsCategory goodsCategory = mallGoodsCategoryMapper.selectByPrimaryKey(goods.getGoodsCategoryId());
        // 分类不存在或者不是三级分类，则该参数字段异常
        if (goodsCategory == null || goodsCategory.getCategoryLevel().intValue() != DreamMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult();
        }
        Goods temp = mallGoodsMapper.selectByPrimaryKey(goods.getGoodsId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        Goods temp2 = mallGoodsMapper.selectByCategoryIdAndName(goods.getGoodsName(), goods.getGoodsCategoryId());
        if (temp2 != null && !temp2.getGoodsId().equals(goods.getGoodsId())) {
            //name和分类id相同且不同id 不能继续修改
            return ServiceResultEnum.SAME_GOODS_EXIST.getResult();
        }
        goods.setUpdateTime(new Date());
        if (mallGoodsMapper.updateByPrimaryKeySelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public Goods getGoodsById(Long id) {
        Goods Goods = mallGoodsMapper.selectByPrimaryKey(id);
        if (Goods == null) {
            DreamMallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        return Goods;
    }

    @Override
    public Boolean batchUpdateSellStatus(Long[] ids, int sellStatus) {
        return mallGoodsMapper.batchUpdateSellStatus(ids, sellStatus) > 0;
    }

}
