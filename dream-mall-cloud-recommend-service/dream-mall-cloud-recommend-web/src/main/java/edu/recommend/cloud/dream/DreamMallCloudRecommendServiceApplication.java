package edu.recommend.cloud.dream;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Date: 2023-11-10 19:57
 * @Author： shenliuming
 * @return：
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("edu.recommend.cloud.dream.dao")
@EnableFeignClients(basePackageClasses = {edu.user.cloud.dream.openfeign.DreamCloudAdminUserServiceFeign.class})
public class DreamMallCloudRecommendServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DreamMallCloudRecommendServiceApplication.class,args);
    }
}
