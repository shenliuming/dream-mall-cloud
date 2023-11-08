package edu.user.cloud.dream.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date: 2023-11-08 15:30
 * @Author： shenliuming
 * @return：
 */
@RestController
public class DreamMallCloudAdminUserController {

    @GetMapping("/users/admin/test/{userId}")
    public String test(@PathVariable("userId") int userId) {
        String userName = "user:" + userId;
        // 返回信息给调用端
        return userName;
    }
}