package com.example.entity;

import java.util.Date;

public class student {
    private Integer stunum;

    private String stuname;

    private Integer stuage;

    private String stusex;

    private Date stubirthday;

    private String stuhobby;
    private int total;

    public Integer getStunum() {
        return stunum;
    }

    public void setStunum(Integer stunum) {
        this.stunum = stunum;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname == null ? null : stuname.trim();
    }

    public Integer getStuage() {
        return stuage;
    }

    public void setStuage(Integer stuage) {
        this.stuage = stuage;
    }

    public String getStusex() {
        return stusex;
    }

    public void setStusex(String stusex) {
        this.stusex = stusex == null ? null : stusex.trim();
    }

    public Date getStubirthday() {
        return stubirthday;
    }

    public void setStubirthday(Date stubirthday) {
        this.stubirthday = stubirthday;
    }

    public String getStuhobby() {
        return stuhobby;
    }

    public void setStuhobby(String stuhobby) {
        this.stuhobby = stuhobby == null ? null : stuhobby.trim();
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}