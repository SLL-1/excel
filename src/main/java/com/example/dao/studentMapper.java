package com.example.dao;

import com.example.entity.SelectTemplate;
import com.example.entity.student;

import java.util.List;

public interface studentMapper {
    int deleteByPrimaryKey(Integer stunum);

    int insert(student record);

    int insertSelective(student record);

    student selectByPrimaryKey(Integer stunum);

    int updateByPrimaryKeySelective(student record);

    int updateByPrimaryKey(student record);
    List<student> selectList(SelectTemplate selectTemplate);
    int  selectListTotal(SelectTemplate selectTemplate);

    void insertByImportId(int importid);
}