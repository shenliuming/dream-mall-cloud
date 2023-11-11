package edu.common.cloud.dream.dto.base;

import org.springframework.util.StringUtils;

/**
 * @Date: 2023-11-08 16:14
 * @Author： shenliuming
 * @return：
 */
public class BaseRespDtoWrapper<T> {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
    private static final String DEFAULT_FAIL_MESSAGE = "FAIL";
    private static final int BaseRespDto_CODE_SUCCESS = 200;
    private static final int BaseRespDto_CODE_SERVER_ERROR = 500;

    public static BaseRespDto genSuccessBaseRespDto() {
        BaseRespDto baseRespDto = new BaseRespDto();
        baseRespDto.setCode(BaseRespDto_CODE_SUCCESS);
        baseRespDto.setMessage(DEFAULT_SUCCESS_MESSAGE);
        return baseRespDto;
    }

    public static BaseRespDto genSuccessBaseRespDto(String message) {
        BaseRespDto baseRespDto = new BaseRespDto();
        baseRespDto.setCode(BaseRespDto_CODE_SUCCESS);
        baseRespDto.setMessage(message);
        return baseRespDto;
    }

    public static BaseRespDto genSuccessBaseRespDto(Object data) {
        BaseRespDto baseRespDto = new BaseRespDto();
        baseRespDto.setCode(BaseRespDto_CODE_SUCCESS);
        baseRespDto.setMessage(DEFAULT_SUCCESS_MESSAGE);
        baseRespDto.setData(data);
        return baseRespDto;
    }

    public static BaseRespDto genFailBaseRespDto(String message) {
        BaseRespDto baseRespDto = new BaseRespDto();
        baseRespDto.setCode(BaseRespDto_CODE_SERVER_ERROR);
        if (StringUtils.isEmpty(message)) {
            baseRespDto.setMessage(DEFAULT_FAIL_MESSAGE);
        } else {
            baseRespDto.setMessage(message);
        }
        return baseRespDto;
    }

    public static BaseRespDto genErrorBaseRespDto(int code, String message) {
        BaseRespDto baseRespDto = new BaseRespDto();
        baseRespDto.setCode(code);
        baseRespDto.setMessage(message);
        return baseRespDto;
    }

}
