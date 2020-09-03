package com.example.service.impl;

import com.example.dao.importdataMapper;
import com.example.dao.studentMapper;
import com.example.entity.SelectTemplate;
import com.example.entity.importdata;
import com.example.entity.student;
import com.example.service.ImportDataService;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ImportDataServiceImpl implements ImportDataService {
    @Autowired
    private importdataMapper importdataMapper;

    //事务控制
    @Transactional
    @Override
    public int insertImportData(importdata importdata) {
        String type=importdata.getType();
        if(type==null||"".equals(type)){
            throw  new RuntimeException("数据类型为空");
        }else{
            try {
                importdata.setCreatedate(new Date());
                int lastnum = importdataMapper.insert(importdata);
                if (lastnum > 0)
                    return importdata.getId();
                else
                    throw new RuntimeException("插入数据失败");
            }catch (Exception e){
                throw new RuntimeException("插入数据失败");
            }
        }

    }
}
