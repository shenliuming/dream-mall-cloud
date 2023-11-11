package edu.user.cloud.dream.openfeign;

import edu.common.cloud.dream.dto.base.BaseRespDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Date: 2023-11-08 20:48
 * @Author： shenliuming
 * @return：
 */
@FeignClient(value = "dream-mall-cloud-user-service",path = "/users")
public interface DreamCloudAdminUserServiceFeign {

    @GetMapping(value = "/admin/{token}")
    BaseRespDto getAdminUserByToken(@PathVariable(value = "token")String token);
}
