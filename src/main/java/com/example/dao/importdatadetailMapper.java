package com.example.dao;

import com.example.entity.importdatadetail;

public interface importdatadetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(importdatadetail record);

    int insertSelective(importdatadetail record);

    importdatadetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(importdatadetail record);

    int updateByPrimaryKey(importdatadetail record);

    void updateStatusByImportId(int importId);
}