package com.webpower.myapp.common;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Liutx
 * @since 2023-05-15 17:54
 */

public class ResultResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功标识符
     */
    private boolean success;

    /**
     * 跟踪号
     */
    private String trace;

    /**
     * 状态码
     */
    private HttpStatus code;

    /**
     * 返回体消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTrace() {
        return trace;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 默认构造方法创建响应体
     *
     * @param success 成功标识符
     * @param code    返回状态码
     * @param message 返回消息
     * @param data    返回数据
     */
    public ResultResponse(boolean success, HttpStatus code, String message, T data) {
        this.trace = UUID.randomUUID().toString();
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 默认构造方法创建响应体
     *
     * @param success 成功标识符
     * @param code    返回状态码
     * @param message 返回消息
     */
    public ResultResponse(boolean success, HttpStatus code, String message) {
        this.trace = UUID.randomUUID().toString();
        this.success = success;
        this.code = code;
        this.message = message;
    }

    /**
     * 无参构造方法
     */
    public ResultResponse() {
    }


    public ResultResponse<T> succeed() {
        this.success = Boolean.TRUE;
        this.code = HttpStatus.OK;
        return this;
    }


    public ResultResponse<T> failed() {
        this.success = Boolean.FALSE;
        return this;
    }


    public ResultResponse<T> message(String message) {
        this.message = message;
        return this;
    }

    public ResultResponse<T> code(HttpStatus code) {
        this.code = code;
        return this;
    }

    public ResultResponse<T> data(T data) {
        this.data = data;
        return this;
    }


    /**
     * 创建响应体实例
     *
     * @param <T> 返回数据
     * @return 响应体
     */
    public static <T> ResultResponse<T> newSuccessInstance() {
        return new ResultResponse<>(Boolean.TRUE, HttpStatus.OK, "操作成功");
    }

    /**
     * 创建响应体实例
     *
     * @param <T> 返回数据
     * @return 响应体
     */
    public static <T> ResultResponse<T> newFailedInstance() {
        return new ResultResponse<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR, "操作失败", null);
    }

}
