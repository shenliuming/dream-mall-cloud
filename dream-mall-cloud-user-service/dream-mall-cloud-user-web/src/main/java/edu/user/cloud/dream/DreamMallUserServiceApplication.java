package edu.user.cloud.dream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Date: 2023-11-08 21:12
 * @Author： shenliuming
 * @return：
 */
@SpringBootApplication
@EnableDiscoveryClient
public class DreamMallUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DreamMallUserServiceApplication.class,args);
    }
}
