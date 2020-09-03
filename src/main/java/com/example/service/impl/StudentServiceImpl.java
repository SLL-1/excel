package com.example.service.impl;

import com.example.dao.importdataMapper;
import com.example.dao.importdatadetailMapper;
import com.example.entity.SelectTemplate;
import com.example.entity.importdata;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.student;
import com.example.dao.studentMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
   private  studentMapper studentMapper;
    @Autowired
    private importdataMapper importdataMapper;
    @Autowired
    private importdatadetailMapper importdatadetailMapper;

    @Override
    public List<student> getStudentList(SelectTemplate selectTemplate) {

            try{
                    return studentMapper.selectList(selectTemplate);

            }catch (Exception e){
                throw new RuntimeException("获取信息失败"+e.getMessage());

            }

    }
    public List<student> getStudentList() {
        return getStudentList(new SelectTemplate());
    }

    @Override
    public int getStudentTotal(SelectTemplate selectTemplate) {
        try{
            return studentMapper.selectListTotal(selectTemplate);
        }catch (Exception e){
            throw new RuntimeException("获取信息失败"+e.getMessage());
        }
    }

    @Override
    public int insertStudent(student student) {
        try{
            return studentMapper.insert(student);
        }catch (Exception e){
            throw new RuntimeException("插入信息失败"+e.getMessage());
        }
    }
    //事务控制
    @Transactional
    @Override
    public boolean insertStudentByImportId(int importId) {
        boolean flag=false;
        try {
            System.out.println(importId);
            studentMapper.insertByImportId(importId);
            importdatadetailMapper.updateStatusByImportId(importId);
            importdata importdata=new importdata();
            importdata.setId(importId);
            importdata.setHandlestatus(1);
            importdata.setHandledate(new Date());
            importdataMapper.updateHandleStatusByImportId(importdata);
            flag=true;
        }catch (Exception e){
            flag=false;
            throw new RuntimeException(e.getMessage());
        }

        return flag;
    }

}
