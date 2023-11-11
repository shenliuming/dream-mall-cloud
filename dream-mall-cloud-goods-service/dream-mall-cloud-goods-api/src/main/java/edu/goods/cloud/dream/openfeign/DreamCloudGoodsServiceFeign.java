package edu.goods.cloud.dream.openfeign;

import edu.common.cloud.dream.dto.base.BaseRespDto;
import edu.common.cloud.dream.dto.goods.GoodsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Date: 2023-11-10 14:44
 * @Author： shenliuming
 * @return：
 */
@FeignClient(value = "dream-mall-cloud-goods-service",path = "/goods")
public interface DreamCloudGoodsServiceFeign {

    @GetMapping(value = "/admin/goodsDetail")
    BaseRespDto<GoodsDto> getGoodsDetail(@RequestParam(value = "goodsId")Long goodsId);
}
