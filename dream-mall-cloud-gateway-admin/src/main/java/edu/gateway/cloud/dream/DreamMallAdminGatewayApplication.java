package edu.gateway.cloud.dream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Date: 2023-11-08 15:45
 * @Author： shenliuming
 * @return：
 */
@SpringBootApplication
@EnableDiscoveryClient
public class DreamMallAdminGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DreamMallAdminGatewayApplication.class,args);
    }
}
