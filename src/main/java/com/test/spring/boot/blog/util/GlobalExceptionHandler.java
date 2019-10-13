package com.test.spring.boot.blog.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.spring.boot.blog.vo.Response;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public Response jsonErrorHandler(MyException e) throws Exception {
    	Response r = new Response(false,e.getMessage());
        return r;
    }
}
