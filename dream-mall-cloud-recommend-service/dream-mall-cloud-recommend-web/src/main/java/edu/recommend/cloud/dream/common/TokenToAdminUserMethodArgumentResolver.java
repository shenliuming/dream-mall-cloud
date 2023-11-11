package edu.recommend.cloud.dream.common;

import edu.common.cloud.dream.dto.base.BaseRespDto;
import edu.common.cloud.dream.entity.annotations.TokenToAdminUser;
import edu.common.cloud.dream.entity.goods.LoginAdminUser;
import edu.common.cloud.dream.exception.DreamMallException;
import edu.user.cloud.dream.openfeign.DreamCloudAdminUserServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.LinkedHashMap;

/**
 * @Date: 2023-11-08 16:35
 * @Author： shenliuming
 * @return：
 */
@Component
public class TokenToAdminUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private DreamCloudAdminUserServiceFeign dreamCloudAdminUserServiceFeign;


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
                BaseRespDto respDto = dreamCloudAdminUserServiceFeign.getAdminUserByToken(token);
                if (respDto == null || respDto.getCode() != 200 || respDto.getData() == null) {
                    DreamMallException.fail("ADMIN_NOT_LOGIN_ERROR");
                }
                LinkedHashMap resultData = (LinkedHashMap) respDto.getData();

                // 将返回的字段封装到LoginAdminUser对象中
                LoginAdminUser loginAdminUser = new LoginAdminUser();
                loginAdminUser.setAdminUserId(Long.valueOf(resultData.get("adminUserId").toString()));
                loginAdminUser.setLoginUserName((String) resultData.get("loginUserName"));
                loginAdminUser.setNickName((String) resultData.get("nickName"));
                loginAdminUser.setLocked(Byte.valueOf(resultData.get("locked").toString()));
                return loginAdminUser;
            } else {
                DreamMallException.fail("ADMIN_NOT_LOGIN_ERROR");
            }
        }
        return null;
    }

}
