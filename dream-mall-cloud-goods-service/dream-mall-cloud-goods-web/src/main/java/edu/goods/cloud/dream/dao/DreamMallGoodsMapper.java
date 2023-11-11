package edu.goods.cloud.dream.dao;

import edu.common.cloud.dream.dto.base.PageQueryUtil;
import edu.common.cloud.dream.dto.goods.StockNumDto;
import edu.common.cloud.dream.entity.goods.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 2023-11-10 15:13
 * @Author： shenliuming
 * @return：
 */
public interface DreamMallGoodsMapper {
    int deleteByPrimaryKey(Long goodsId);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Long goodsId);

    Goods selectByCategoryIdAndName(@Param("goodsName") String goodsName, @Param("goodsCategoryId") Long goodsCategoryId);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);

    List<Goods> findGoodsList(PageQueryUtil pageUtil);

    int getTotalGoods(PageQueryUtil pageUtil);

    List<Goods> selectByPrimaryKeys(List<Long> goodsIds);

    List<Goods> findGoodsListBySearch(PageQueryUtil pageUtil);

    int getTotalGoodsBySearch(PageQueryUtil pageUtil);

    int batchInsert(@Param("GoodsList") List<Goods> GoodsList);

    int updateStockNum(@Param("stockNumDTOS") List<StockNumDto> stockNumDTOS);

    int batchUpdateSellStatus(@Param("orderIds")Long[] orderIds,@Param("sellStatus") int sellStatus);

}
