package com.example.dao;

import com.example.entity.importdata;

public interface importdataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(importdata record);

    int insertSelective(importdata record);

    importdata selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(importdata record);

    int updateByPrimaryKey(importdata record);

    void updateHandleStatusByImportId(importdata importdata);
}