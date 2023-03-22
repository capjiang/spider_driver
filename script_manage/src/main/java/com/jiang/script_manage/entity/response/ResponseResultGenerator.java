package com.jiang.script_manage.entity.response;

public class ResponseResultGenerator {
    public static final String DEFAULT_SUCCESS_MSG = "success";
    public static final String DEFAULT_ERROR_MSG = "unknown error";
    public static final String DEFAULT_BAD_REQUEST_MSG = "incorrect data input";

    public static final int DEFAULT_SUCCESS_CODE = 200;
    public static final int BAD_REQUEST_CODE = 400;
    public static final int UNAUTHORIZED_CODE = 401;
    public static final int FORBIDDEN_CODE = 403;
    public static final int NOT_FOUND_CODE = 404;
    public static final int UNKNOWN_ERROR_CODE = 500;

    private ResponseResultGenerator() {
    }

    public static ResponseResult genSuccessResult() {
        return genSuccessResult(null, null);
    }

    public static ResponseResult genSuccessResult(Object data) {
        return genSuccessResult(data, null);
    }

    public static ResponseResult genSuccessResult(String token) {
        return genSuccessResult(null, token);
    }

    public static ResponseResult genSuccessResult(Object data, String token) {
        return new ResponseResult(DEFAULT_SUCCESS_CODE, data, DEFAULT_SUCCESS_MSG, token);
    }

    public static ResponseResult genSuccessResult(Object data, String msg, String token) {
        return new ResponseResult(DEFAULT_SUCCESS_CODE, data, msg, token);
    }

    public static ResponseResult genBadRequestResult() {
        return new ResponseResult(BAD_REQUEST_CODE, null, DEFAULT_BAD_REQUEST_MSG, null);
    }

    public static ResponseResult genBadRequestResult(String msg) {
        return new ResponseResult(BAD_REQUEST_CODE, null, msg, null);
    }

    public static ResponseResult genUnauthorizedResult() {
        return new ResponseResult(UNAUTHORIZED_CODE, null, DEFAULT_ERROR_MSG, null);
    }

    public static ResponseResult genForbiddenResult() {
        return new ResponseResult(FORBIDDEN_CODE, null, DEFAULT_ERROR_MSG, null);
    }

    public static ResponseResult genNotFoundResult() {
        return new ResponseResult(NOT_FOUND_CODE, null, DEFAULT_ERROR_MSG, null);
    }

    public static ResponseResult genUnknownErrorResult() {
        return new ResponseResult(UNKNOWN_ERROR_CODE, null, DEFAULT_ERROR_MSG, null);
    }

    public static ResponseResult genFailResult(int code, Object data, String msg, String token) {
        return new ResponseResult(code, data, msg, token);
    }
}
