package com.example.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import com.example.entity.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(cn.dev33.satoken.exception.NotLoginException.class)
    public ResponseResult<?> handleNotLoginException(NotLoginException e) {
        logger.error("用户未登录或Token无效: {}", e.getMessage(), e);
        return ResponseResult.error("用户未登录或Token无效");
    }

    @ExceptionHandler(cn.dev33.satoken.exception.NotRoleException.class)
    public ResponseResult<?> handleNotLoginException(NotRoleException e) {
        logger.error("用户未登录或角色受限: {}", e.getMessage(), e);
        return ResponseResult.error("用户未登录或角色受限");
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult<?> handleOtherException(Exception e) {
        logger.error("服务器内部错误: {}", e.getMessage(), e);
        return ResponseResult.error("服务器内部错误");
    }

    @ExceptionHandler(BindException.class)
    public ResponseResult<?> handleValidationExceptions(BindException e) {
        logger.error("服务器内部错误: {}", e.getMessage(), e);
        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
        return ResponseResult.error(errorMessage);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseResult<?> handleBusinessException(BusinessException e) {
        logger.error("业务异常: {}", e.getMessage(), e);
        return ResponseResult.error(e.getCode(), e.getMessage(),e.getData());
    }
}
