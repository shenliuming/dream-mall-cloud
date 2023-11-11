package edu.goods.cloud.dream.handler;

import edu.common.cloud.dream.dto.base.BaseRespDto;
import edu.common.cloud.dream.exception.DreamMallException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Date: 2023-11-08 16:32
 * @Author： shenliuming
 * @return：
 */
@RestControllerAdvice
public class GoodsServiceExceptionHandler {

    @ExceptionHandler(BindException.class)
    public Object bindException(BindException e) {
        BaseRespDto BaseRespDto = new BaseRespDto();
        BaseRespDto.setCode(510);
        BindingResult bindingResult = e.getBindingResult();
        BaseRespDto.setMessage(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        return BaseRespDto;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object bindException(MethodArgumentNotValidException e) {
        BaseRespDto BaseRespDto = new BaseRespDto();
        BaseRespDto.setCode(510);
        BindingResult bindingResult = e.getBindingResult();
        BaseRespDto.setMessage(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        return BaseRespDto;
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e, HttpServletRequest req) {
        BaseRespDto BaseRespDto = new BaseRespDto();
        BaseRespDto.setCode(500);
        //区分是否为自定义异常
        if (e instanceof DreamMallException) {
            BaseRespDto.setMessage(e.getMessage());
            if (e.getMessage().equals("ADMIN_NOT_LOGIN_ERROR") || e.getMessage().equals("ADMIN_TOKEN_EXPIRE_ERROR")) {
                BaseRespDto.setCode(419);
            }
        } else {
            e.printStackTrace();
            BaseRespDto.setMessage("未知异常，请查看控制台日志并检查配置文件。");
        }
        return BaseRespDto;

    }


}
