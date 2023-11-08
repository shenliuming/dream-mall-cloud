package edu.common.cloud.dream.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Date: 2023-11-08 16:15
 * @Author： shenliuming
 * @return：
 */
public class BaseRespDto<T> implements Serializable {

    private static final long serialVersionUID = -1L;
    // 数据结果，泛型，可以是列表、单个对象、数字、布尔值等
    @ApiModelProperty("返回数据")
    private T data;
    // 业务码，比如成功、失败、权限不足等 code，可自行定义
    @ApiModelProperty("返回码")
    private int code;
    // 返回信息，后端在进行业务处理后返回给前端一个提示信息，可自行定义
    @ApiModelProperty("返回信息")
    private String message;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseRespDto{" +
                "data=" + data +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
