package edu.common.cloud.dream.dto.goods;

import lombok.Data;

/**
 * @Date: 2023-11-10 14:42
 * @Author： shenliuming
 * @return：
 */
@Data
public class GoodsDto {
    private Long goodsId;

    private String goodsName;

    private String goodsIntro;

    private Long goodsCategoryId;

    private String goodsCoverImg;

    private String goodsCarousel;

    private Integer originalPrice;

    private Integer sellingPrice;

    private Integer stockNum;

    private String tag;

    private Byte goodsSellStatus;
}
