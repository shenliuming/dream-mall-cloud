package edu.recommend.cloud.dream.dao;

import edu.common.cloud.dream.dto.base.PageQueryUtil;
import edu.common.cloud.dream.entity.recommend.IndexConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 2023-11-11 13:58
 * @Author： shenliuming
 * @return：
 */
public interface IndexConfigMapper {

    int deleteByPrimaryKey(Long configId);

    int insert(IndexConfig record);

    int insertSelective(IndexConfig record);

    IndexConfig selectByPrimaryKey(Long configId);

    IndexConfig selectByTypeAndGoodsId(@Param("configType") int configType, @Param("goodsId") Long goodsId);

    int updateByPrimaryKeySelective(IndexConfig record);

    int updateByPrimaryKey(IndexConfig record);

    List<IndexConfig> findIndexConfigList(PageQueryUtil pageUtil);

    int getTotalIndexConfigs(PageQueryUtil pageUtil);

    int deleteBatch(Long[] ids);

    List<IndexConfig> findIndexConfigsByTypeAndNum(@Param("configType") int configType, @Param("number") int number);
}
