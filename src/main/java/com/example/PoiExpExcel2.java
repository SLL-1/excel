package com.example;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 数据写入excel表格,HSSF只适用于97-2003版本，XSSF创建高版本的
 * 创建的时候多采用创建低版本的，这样高版本的软件也可以打开
 */
public class PoiExpExcel2 {
    public static void main(String[] args) {
        //第一行的数据=表头=列名
         String[] title={"id","name","sex"};
         //创建excel表格
        XSSFWorkbook Workbook=new XSSFWorkbook();
        //创建工作簿
        XSSFSheet sheet=Workbook.createSheet();
        //创建工作簿的第一行
        XSSFRow  Row=sheet.createRow(0);
        XSSFCell cell;
        //填写第一行数据
        for (int i=0;i<title.length;i++){
            cell= Row.createCell(i);
            cell.setCellValue(title[i]);
        }
        for (int j=1;j<10;j++){
            XSSFRow Row1=sheet.createRow(j);
            cell=Row1.createCell(0);
            cell.setCellValue(j);
            cell=Row1.createCell(1);
            cell.setCellValue("姓名"+j);
            cell=Row1.createCell(2);
            cell.setCellValue("女");
        }
        File file=new File("C:/Users/DELL/Desktop/poi_test.xlsx");
        try {
            file.createNewFile();
            FileOutputStream stream= FileUtils.openOutputStream(file);
            Workbook.write(stream);
            stream.close();
            Workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
