package edu.recommend.cloud.dream.service.impl;

import edu.common.cloud.dream.dto.base.BaseRespDto;
import edu.common.cloud.dream.dto.base.PageQueryUtil;
import edu.common.cloud.dream.dto.base.PageResult;
import edu.common.cloud.dream.dto.goods.GoodsDto;
import edu.common.cloud.dream.entity.recommend.IndexConfig;
import edu.common.cloud.dream.enums.ServiceResultEnum;
import edu.goods.cloud.dream.openfeign.DreamCloudGoodsServiceFeign;
import edu.recommend.cloud.dream.dao.IndexConfigMapper;
import edu.recommend.cloud.dream.service.DreamMallIndexConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Date: 2023-11-11 14:16
 * @Author： shenliuming
 * @return：
 */
@Service
public class DreamMallIndexConfigServiceImpl implements DreamMallIndexConfigService {

    @Autowired
    private IndexConfigMapper indexConfigMapper;

    @Autowired
    private DreamCloudGoodsServiceFeign goodsServiceFeign;

    @Override
    public PageResult getConfigsPage(PageQueryUtil pageUtil) {
        List<IndexConfig> indexConfigs = indexConfigMapper.findIndexConfigList(pageUtil);
        int total = indexConfigMapper.getTotalIndexConfigs(pageUtil);
        PageResult pageResult = new PageResult(indexConfigs, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveIndexConfig(IndexConfig indexConfig) {
        BaseRespDto<GoodsDto> goodsDetailResult = goodsServiceFeign.getGoodsDetail(indexConfig.getGoodsId());
        if (goodsDetailResult == null || goodsDetailResult.getCode() != 200) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        if (indexConfigMapper.selectByTypeAndGoodsId(indexConfig.getConfigType(), indexConfig.getGoodsId()) != null) {
            return ServiceResultEnum.SAME_INDEX_CONFIG_EXIST.getResult();
        }
        if (indexConfigMapper.insertSelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateIndexConfig(IndexConfig indexConfig) {
        BaseRespDto<GoodsDto> goodsDetailResult  = goodsServiceFeign.getGoodsDetail(indexConfig.getGoodsId());
        if (goodsDetailResult == null || goodsDetailResult.getCode() != 200) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        IndexConfig temp = indexConfigMapper.selectByPrimaryKey(indexConfig.getConfigId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        IndexConfig temp2 = indexConfigMapper.selectByTypeAndGoodsId(indexConfig.getConfigType(), indexConfig.getGoodsId());
        if (temp2 != null && !temp2.getConfigId().equals(indexConfig.getConfigId())) {
            //goodsId相同且不同id 不能继续修改
            return ServiceResultEnum.SAME_INDEX_CONFIG_EXIST.getResult();
        }
        indexConfig.setUpdateTime(new Date());
        if (indexConfigMapper.updateByPrimaryKeySelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public IndexConfig getIndexConfigById(Long id) {
        return indexConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean deleteBatch(Long[] ids) {
        if (ids.length < 1) {
            return false;
        }
        //删除数据
        return indexConfigMapper.deleteBatch(ids) > 0;
    }

}
