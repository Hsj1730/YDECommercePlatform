package com.hsjnb.backend.utils;

import com.hsjnb.backend.enums.ResponseCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * █████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 * ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 * ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 * ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 * ░     ░ ░      ░  ░
 *
 * @author : Hsj1730
 * @version : 1.0
 * @date : Created in 2022/01/15 16:00
 * @description : 通用返回类
 */

@Data
public class ResponseDataUtils<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;

    /**
     * 自定义code，message,也可以在ResponseCodeEnum中提前规定
     */
    public static <T> ResponseDataUtils<T> custom(Integer code, String msg, T data) {
        ResponseDataUtils<T> responseDataUtils = new ResponseDataUtils<>();
        responseDataUtils.setCode(code);
        responseDataUtils.setMsg(msg);
        responseDataUtils.setData(data);
        return responseDataUtils;
    }

    /**
     * 自定义code，message,也可以在ResponseCodeEnum中提前规定
     */
    public static ResponseDataUtils<Object> custom(Integer code, String msg) {
        ResponseDataUtils<Object> responseDataUtils = new ResponseDataUtils<>();
        responseDataUtils.setCode(code);
        responseDataUtils.setMsg(msg);
        return responseDataUtils;
    }

    public static <T> ResponseDataUtils<T> success(String msg, T data) {
        ResponseDataUtils<T> responseDataUtils = new ResponseDataUtils<>();
        responseDataUtils.setCode(ResponseCodeEnum.SUCCESS.getCode());
        responseDataUtils.setMsg(msg == null ? ResponseCodeEnum.SUCCESS.getMsg() : msg);
        responseDataUtils.setData(data);
        return responseDataUtils;
    }

    public static ResponseDataUtils<Object> success(String msg) {
        ResponseDataUtils<Object> responseDataUtils = new ResponseDataUtils<>();
        responseDataUtils.setCode(ResponseCodeEnum.SUCCESS.getCode());
        responseDataUtils.setMsg(msg == null ? ResponseCodeEnum.SUCCESS.getMsg() : msg);
        return responseDataUtils;
    }

    public static <T> ResponseDataUtils<T> success(T data) {
        ResponseDataUtils<T> responseDataUtils = new ResponseDataUtils<>();
        responseDataUtils.setCode(ResponseCodeEnum.SUCCESS.getCode());
        responseDataUtils.setMsg(ResponseCodeEnum.SUCCESS.getMsg());
        responseDataUtils.setData(data);
        return responseDataUtils;
    }

    public static <T> ResponseDataUtils<T> fail(String msg, T data) {
        ResponseDataUtils<T> responseDataUtils = new ResponseDataUtils<>();
        responseDataUtils.setCode(ResponseCodeEnum.FAIL.getCode());
        responseDataUtils.setMsg(msg == null ? ResponseCodeEnum.SUCCESS.getMsg() : msg);
        responseDataUtils.setData(data);
        return responseDataUtils;
    }

    public static ResponseDataUtils<Object> fail(String msg) {
        ResponseDataUtils<Object> responseDataUtils = new ResponseDataUtils<>();
        responseDataUtils.setCode(ResponseCodeEnum.FAIL.getCode());
        responseDataUtils.setMsg(msg == null ? ResponseCodeEnum.SUCCESS.getMsg() : msg);
        return responseDataUtils;
    }

    public static <T> ResponseDataUtils<T> fail(T data) {
        ResponseDataUtils<T> responseDataUtils = new ResponseDataUtils<>();
        responseDataUtils.setCode(ResponseCodeEnum.FAIL.getCode());
        responseDataUtils.setMsg(ResponseCodeEnum.SUCCESS.getMsg());
        responseDataUtils.setData(data);
        return responseDataUtils;
    }

    public static ResponseDataUtils<Throwable> error(Throwable e) {
        ResponseDataUtils<Throwable> responseDataUtils = new ResponseDataUtils<>();
        responseDataUtils.setCode(ResponseCodeEnum.FAIL.getCode());
        responseDataUtils.setMsg("服务器繁忙，请稍候再试");
        responseDataUtils.setData(e);
        return responseDataUtils;
    }

}
