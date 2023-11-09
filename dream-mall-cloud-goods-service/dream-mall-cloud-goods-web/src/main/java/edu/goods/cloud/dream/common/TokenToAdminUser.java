package edu.goods.cloud.dream.common;

import java.lang.annotation.*;

/**
 * @Date: 2023-11-08 16:34
 * @Author： shenliuming
 * @return：
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenToAdminUser {
    String value() default "adminUser";
}
