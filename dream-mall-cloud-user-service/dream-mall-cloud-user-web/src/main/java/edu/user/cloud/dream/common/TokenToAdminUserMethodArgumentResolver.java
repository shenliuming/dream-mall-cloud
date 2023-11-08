package edu.user.cloud.dream.common;

import edu.common.cloud.dream.exception.DreamMallException;
import edu.user.cloud.dream.dao.AdminUserTokenMapper;
import edu.user.cloud.dream.entity.AdminUserToken;

import org.springframework.core.MethodParameter;
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
    private AdminUserTokenMapper adminUserTokenMapper;

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
                AdminUserToken adminUserToken = adminUserTokenMapper.selectByToken(token);
                if (adminUserToken == null) {
                    DreamMallException.fail("ADMIN_NOT_LOGIN_ERROR");
                } else if (adminUserToken.getExpireTime().getTime() <= System.currentTimeMillis()) {
                    DreamMallException.fail("ADMIN_TOKEN_EXPIRE_ERROR");
                }
                return adminUserToken;
            } else {
                DreamMallException.fail("ADMIN_NOT_LOGIN_ERROR");
            }
        }
        return null;
    }

}
