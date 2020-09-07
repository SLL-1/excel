package com.example.config.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

/**
 *方式一：error页面：jsp配置，错误页面放置在jsp配置的文件底下才生效
 * 方式二：@controller+ @ExceptionHandler ：只在当前的controller类下生效
 * 方式三：@controllerAdvice+ @ExceptionHandler：全局的异常，可以返还页面和错误信息，不同的类型写多个方法
 * 方式四：@configuration+SimpleMappingExceptionResolver ：返回值为SimpleMappingExceptionResolver类型的，只返回页面，同一个方法，根据异常的类型加载不同的界面
 * 方式五：implements HandlerExceptionResolver ：可以返还页面和错误信息，同一个方法，根据异常的类型加载不同的界面
 * 方法四和方法五不能在同一个类中
 *异常先后顺序：方式三 -> 方式五 -> 方式四 -> 方式一
 */
@Configuration
public class ExceptionConfiguration implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("errorInfo",e.toString());
        if(e instanceof NullPointerException){
            modelAndView.setViewName("configImplementerror");
        }
        return  modelAndView;
    }
}
