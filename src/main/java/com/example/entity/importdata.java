package com.example.entity;

import java.util.Date;

public class importdata {
    private Integer id;

    private String type;

    private Date createdate;

    private Integer status;

    private Date handledate;

    private Integer handlestatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getHandledate() {
        return handledate;
    }

    public void setHandledate(Date handledate) {
        this.handledate = handledate;
    }

    public Integer getHandlestatus() {
        return handlestatus;
    }

    public void setHandlestatus(Integer handlestatus) {
        this.handlestatus = handlestatus;
    }
}