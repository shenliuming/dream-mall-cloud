package edu.user.cloud.dream.config;

import edu.user.cloud.dream.util.DreamMallException;
import edu.user.cloud.dream.util.Result;
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
public class AdminUserServiceExceptionHandler {
    
    @ExceptionHandler(BindException.class)
    public Object bindException(BindException e) {
        Result result = new Result();
        result.setCode(510);
        BindingResult bindingResult = e.getBindingResult();
        result.setMessage(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        return result;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object bindException(MethodArgumentNotValidException e) {
        Result result = new Result();
        result.setCode(510);
        BindingResult bindingResult = e.getBindingResult();
        result.setMessage(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        return result;
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e, HttpServletRequest req) {
        Result result = new Result();
        result.setCode(500);
        if (e instanceof DreamMallException) {
            result.setMessage(e.getMessage());
            if (e.getMessage().equals("ADMIN_NOT_LOGIN_ERROR") || e.getMessage().equals("ADMIN_TOKEN_EXPIRE_ERROR")) {
                result.setCode(419);
            }
        } else {
            e.printStackTrace();
            result.setMessage("未知异常，请查看控制台日志并检查配置文件。");
        }
        return result;
    }

}
