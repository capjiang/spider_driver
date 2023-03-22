package com.jiang.user_manage.exception;

public enum ExceptionEnum implements BaseErrorInfo {

    // 数据操作错误定义
    SUCCESS(200000, "成功"),
    REQ_BODY_NOT_MATCH(400000, "请求数据格式不符合!"),
    SERVER_UNKNOWN_ERROR(500000, "服务未知异常");


    private final int resCode;
    private final String resMsg;

    ExceptionEnum(int resCode, String resMsg) {
        this.resCode = resCode;
        this.resMsg = resMsg;
    }

    @Override
    public int getResultCode() {
        return this.resCode;
    }

    @Override
    public String getResultMsg() {
        return this.resMsg;
    }
}
