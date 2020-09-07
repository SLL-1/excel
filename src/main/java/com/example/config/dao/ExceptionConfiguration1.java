package com.example.config.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;

/**
 * SimpleMappingExceptionResolver方式
 */
@Configuration
 public class ExceptionConfiguration1  {
    @Bean
     public SimpleMappingExceptionResolver getsimpleMappingExceptionResolver(){
         SimpleMappingExceptionResolver resolver=new SimpleMappingExceptionResolver();
         Properties properties=new Properties();
          properties.put("java.lang.NullPointerException","configerror");
          resolver.setExceptionMappings(properties);
          return resolver;

      }
  }