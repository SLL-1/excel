package com.example;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;

/**
 * 从excel表格中读取数据
 */
public class JxlReadExcel {
    public static void main(String[] args) {
        try {
            //获取workbook
            Workbook workbook=Workbook.getWorkbook(new File("C:/Users/DELL/Desktop/jxl_test.xls"));
            //获取第一个工作簿
            Sheet sheet =workbook.getSheet(0);
            //输出数据
            for (int i=0;i<sheet.getRows();i++){
                for (int j=0;j<sheet.getColumns();j++){
                    System.out.print(sheet.getCell(j,i).getContents()+"  ");
                }
                System.out.println();
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
