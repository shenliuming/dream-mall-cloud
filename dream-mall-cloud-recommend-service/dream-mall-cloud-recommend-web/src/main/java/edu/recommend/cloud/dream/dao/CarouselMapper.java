package edu.recommend.cloud.dream.dao;

import edu.common.cloud.dream.dto.base.PageQueryUtil;
import edu.common.cloud.dream.entity.recommend.Carousel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 2023-11-11 13:58
 * @Author： shenliuming
 * @return：
 */
public interface CarouselMapper {
    int deleteByPrimaryKey(Integer carouselId);

    int insert(Carousel record);

    int insertSelective(Carousel record);

    Carousel selectByPrimaryKey(Integer carouselId);

    int updateByPrimaryKeySelective(Carousel record);

    int updateByPrimaryKey(Carousel record);

    List<Carousel> findCarouselList(PageQueryUtil pageUtil);

    int getTotalCarousels(PageQueryUtil pageUtil);

    int deleteBatch(Long[] ids);

    List<Carousel> findCarouselsByNum(@Param("number") int number);
}
