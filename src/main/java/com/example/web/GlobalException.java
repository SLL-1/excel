package com.example.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理类
 */
@ControllerAdvice
public class GlobalException {
    //针对不同的异常类型可以写多个方法，不同类型返回不同页面
    @ExceptionHandler(value = {ArithmeticException.class})
    public ModelAndView getArithmeticException(Exception e){

        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("errorInfo",e.toString());
        modelAndView.setViewName("globalerror");
        return modelAndView;
    }


}
