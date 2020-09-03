package com.example.service.impl;

import com.example.dao.importdataMapper;
import com.example.dao.importdatadetailMapper;
import com.example.entity.importdata;
import com.example.entity.importdatadetail;
import com.example.service.ImportDataDetailService;
import com.example.service.ImportDataService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ImportDataDetailServiceImpl implements ImportDataDetailService {
    @Autowired
    private importdatadetailMapper importdatadetailMapper;

    //事务控制
    @Transactional
    @Override
    public int insertImportDataDetail(importdatadetail importdatadetail) {
       int importdataid=importdatadetail.getImportid();
        if(importdataid==0){
            throw  new RuntimeException("数据类型为空");
        }else{
            try {

                int lastnum = importdatadetailMapper.insertSelective(importdatadetail);
                if (lastnum > 0)
                    return importdatadetail.getId();
                else
                    throw new RuntimeException("插入数据失败");
            }catch (Exception e){
                throw new RuntimeException("插入数据失败");
            }
        }

    }
}
