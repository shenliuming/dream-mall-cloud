package edu.goods.cloud.dream;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Date: 2023-11-09 15:42
 * @Author： shenliuming
 * @return：
 */
@EnableFeignClients(basePackageClasses = {edu.user.cloud.dream.openfeign.DreamCloudAdminUserServiceFeign.class})
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("edu.goods.cloud.dream.dao")
public class DreamMallCloudGoodsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DreamMallCloudGoodsServiceApplication.class,args);
    }
}
