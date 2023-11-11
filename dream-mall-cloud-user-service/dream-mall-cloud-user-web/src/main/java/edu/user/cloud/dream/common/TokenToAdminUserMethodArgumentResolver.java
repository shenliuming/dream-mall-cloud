package edu.user.cloud.dream.common;

import edu.common.cloud.dream.exception.DreamMallException;
import edu.common.cloud.dream.entity.base.AdminUserToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @Date: 2023-11-08 16:35
 * @Author： shenliuming
 * @return：
 */
@Component
public class TokenToAdminUserMethodArgumentResolver implements HandlerMethodArgumentResolver {


    @Autowired
    private RedisTemplate redisTemplate;
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(TokenToAdminUser.class)) {
            return true;
        }
        return false;
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        if (parameter.getParameterAnnotation(TokenToAdminUser.class) instanceof TokenToAdminUser) {
            String token = webRequest.getHeader("token");
            if (null != token && !"".equals(token) && token.length() == 32) {
                ValueOperations<String, AdminUserToken> opsForAdminUserToken = redisTemplate.opsForValue();
                AdminUserToken adminUserToken = opsForAdminUserToken.get(token);
                if (adminUserToken == null) {
                    DreamMallException.fail("ADMIN_NOT_LOGIN_ERROR");
                }
                return adminUserToken;
            } else {
                DreamMallException.fail("ADMIN_NOT_LOGIN_ERROR");
            }
        }
        return null;
    }

}
