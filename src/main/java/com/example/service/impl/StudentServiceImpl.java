package com.example.service.impl;

import com.example.entity.SelectTemplate;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.student;
import com.example.dao.studentMapper;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
   private  studentMapper studentMapper;

    @Override
    public List<student> getStudentList(SelectTemplate selectTemplate) {
//        SelectTemplate selectTemplate1=new SelectTemplate();
//        selectTemplate.setPage(1);
//        selectTemplate.setRows(5);
//        selectTemplate.setSort("stuname");
//        selectTemplate.setOrder("desc");
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

}
