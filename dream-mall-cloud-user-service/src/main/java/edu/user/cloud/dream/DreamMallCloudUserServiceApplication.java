package edu.user.cloud.dream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class DreamMallCloudUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DreamMallCloudUserServiceApplication.class,args);
    }
}
