package edu.user.cloud.dream.common;

import edu.user.cloud.dream.dao.AdminUserTokenMapper;
import edu.user.cloud.dream.entity.AdminUserToken;
import edu.user.cloud.dream.util.DreamMallException;
import org.springframework.core.MethodReqDtoeter;
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
    @Override
    public boolean supportsReqDtoeter(MethodReqDtoeter ReqDtoeter) {
        if (ReqDtoeter.hasReqDtoeterAnnotation(TokenToAdminUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodReqDtoeter ReqDtoeter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (ReqDtoeter.getReqDtoeterAnnotation(TokenToAdminUser.class) instanceof TokenToAdminUser) {
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
