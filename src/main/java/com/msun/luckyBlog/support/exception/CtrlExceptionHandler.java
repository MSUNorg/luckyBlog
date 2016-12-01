/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.luckyBlog.support.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理类
 * 
 * @author zxc Dec 1, 2016 6:38:25 PM
 */
@ControllerAdvice
public class CtrlExceptionHandler {

    static final Logger log = LoggerFactory.getLogger(CtrlExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView handleIOException(Exception e) {
        log.info(e.getLocalizedMessage());
        return new ModelAndView("/error");
    }
}
