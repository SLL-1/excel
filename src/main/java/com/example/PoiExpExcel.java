package com.example;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 数据写入excel表格,HSSF只适用于97-2003版本，XSSF创建高版本的
 * 创建的时候多采用创建低版本的，这样高版本的软件也可以打开
 */
public class PoiExpExcel {
    public static void main(String[] args) {
        //第一行的数据=表头=列名
         String[] title={"id","name","sex"};
         //创建excel表格
        HSSFWorkbook hssfWorkbook=new HSSFWorkbook();
        //创建工作簿
        HSSFSheet sheet=hssfWorkbook.createSheet();
        //创建工作簿的第一行
        HSSFRow hssfRow=sheet.createRow(0);
        HSSFCell cell;
        //填写第一行数据
        for (int i=0;i<title.length;i++){
            cell=hssfRow.createCell(i);
            cell.setCellValue(title[i]);
        }
        for (int j=1;j<10;j++){
            HSSFRow Row=sheet.createRow(j);
            cell=Row.createCell(0);
            cell.setCellValue(j);
            cell=Row.createCell(1);
            cell.setCellValue("姓名"+j);
            cell=Row.createCell(2);
            cell.setCellValue("女");
        }
        File file=new File("C:/Users/DELL/Desktop/poi_test.xls");
        try {
            file.createNewFile();
            FileOutputStream stream= FileUtils.openOutputStream(file);
            hssfWorkbook.write(stream);
            stream.close();
            hssfWorkbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
