package com.example;


import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.io.IOException;

/**
 * 数据写入excel表格
 */
public class JxlExpExcel {
    public static void main(String[] args) {
        //excel表格的表头=列名
        String[] title= new String[]{"id", "name", "sex"};
        //创建excel表格文件
        File file=new File("C:/Users/DELL/Desktop/jxl_test.xls");
        try {
            file.createNewFile();
            //创建工作簿
            WritableWorkbook workbook  = Workbook.createWorkbook(file);
            //创建sheet
            WritableSheet sheet=workbook.createSheet("sheet1",0);//sheet1是工作簿名称
            Label label;
            //插入表头=列名
            for (int i=0;i<title.length;i++){
                label=new Label(i,0,title[i]);
                sheet.addCell(label);
            }
            //插入10行数据
            for (int i=1;i<10;i++){
                label=new Label(0,i,i+"");
                sheet.addCell(label);
                label=new Label(1,i,"姓名"+i);
                sheet.addCell(label);
                label=new Label(2,i,"男");
                sheet.addCell(label);
            }
            workbook.write();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
