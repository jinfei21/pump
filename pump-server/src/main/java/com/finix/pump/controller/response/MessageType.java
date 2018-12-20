package com.finix.pump.controller.response;

/**
 * common web service response, which provide uniform format of response code and message
 *
 * @author huangyinhuang
 * @date 2017/8/8
 */
public enum MessageType {

    SUCCESS(0, "请求成功完成。"),
    ERROR(-1, "发现错误。"),
    UNKNOWN(-4, "未知错误。");

    private Integer code;
    private String msg;

    MessageType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

}
