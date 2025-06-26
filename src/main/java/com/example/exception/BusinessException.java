package com.example.exception;

import static com.example.constant.StatusCode.ERROR;

public class BusinessException extends RuntimeException {
    private final int code;
    private Object data;

    public BusinessException(String message) {
        super(message);
        this.code = ERROR;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(int code, String message, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }
}
