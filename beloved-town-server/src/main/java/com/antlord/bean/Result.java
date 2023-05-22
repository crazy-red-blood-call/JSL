package com.antlord.bean;

import com.antlord.enums.RsultCodeEnum;
import lombok.Data;

@Data
public class Result {

    /**
     * 响应码:000000-成功
     */
    private String code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应数据
     */
    private Object data;

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Result success() {
        return new Result(RsultCodeEnum.SUCCESS.getCode(), RsultCodeEnum.SUCCESS.getMsg());
    }

    public static Result success(Object data) {
        Result result = new Result(RsultCodeEnum.SUCCESS.getCode(), RsultCodeEnum.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static Result failed() {
        return new Result(RsultCodeEnum.FAILED.getCode(), RsultCodeEnum.FAILED.getMsg());
    }

    public static Result failed(Object data) {
        Result result = new Result(RsultCodeEnum.FAILED.getCode(), RsultCodeEnum.FAILED.getMsg());
        result.setData(data);
        return result;
    }
    public static Result failed(RsultCodeEnum rsultCodeEnum) {
        Result result = new Result(rsultCodeEnum.getCode(), rsultCodeEnum.getMsg());
        return result;
    }

    public static Result error() {
        return new Result(RsultCodeEnum.ERROR.getCode(), RsultCodeEnum.ERROR.getMsg());
    }

    public static Result error(RsultCodeEnum rsultCodeEnum) {
        return new Result(rsultCodeEnum.getCode(), rsultCodeEnum.getMsg());
    }

}
