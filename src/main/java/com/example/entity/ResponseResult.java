package com.example.entity;

import com.example.constant.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T> {
    private int code;
    private String message;
    private T data;

    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>(StatusCode.SUCCESS, "操作成功", null);
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(StatusCode.SUCCESS, "操作成功", data);
    }

    public static <T> ResponseResult<T> error(String message) {
        return new ResponseResult<>(StatusCode.ERROR, message, null);
    }

    public static <T> ResponseResult<T> error(int code, String message) {
        return new ResponseResult<>(code, message, null);
    }

    public static <T> ResponseResult<T> error(int code, String message,T data) {
        return new ResponseResult<>(code, message, data);
    }

}