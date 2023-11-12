package edu.recommend.cloud.dream.service;

import edu.common.cloud.dream.dto.base.PageQueryUtil;
import edu.common.cloud.dream.dto.base.PageResult;
import edu.common.cloud.dream.entity.recommend.IndexConfig;

/**
 * @Date: 2023-11-11 14:16
 * @Author： shenliuming
 * @return：
 */
public interface DreamMallIndexConfigService {

    PageResult getConfigsPage(PageQueryUtil pageUtil);

    String saveIndexConfig(IndexConfig indexConfig);

    String updateIndexConfig(IndexConfig indexConfig);

    IndexConfig getIndexConfigById(Long id);

    Boolean deleteBatch(Long[] ids);
}
