package com.example.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TaskScheduled {
    //initialDelay第一次开始任务时间在项目启动后的5s，fixedDelay每10s任务执行一次
    @Scheduled(initialDelay = 5000,fixedDelay = 10000)
    public void task(){
        System.out.println("定时任务开始"+ new Date());
    }
}
