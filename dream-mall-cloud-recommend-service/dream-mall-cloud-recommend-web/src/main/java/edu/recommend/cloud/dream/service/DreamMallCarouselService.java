package edu.recommend.cloud.dream.service;

import edu.common.cloud.dream.dto.base.PageQueryUtil;
import edu.common.cloud.dream.dto.base.PageResult;
import edu.common.cloud.dream.entity.recommend.Carousel;

/**
 * @Date: 2023-11-11 14:15
 * @Author： shenliuming
 * @return：
 */
public interface DreamMallCarouselService {

    PageResult getCarouselPage(PageQueryUtil pageUtil);

    String saveCarousel(Carousel carousel);

    String updateCarousel(Carousel carousel);

    Carousel getCarouselById(Integer id);

    Boolean deleteBatch(Long[] ids);
}
