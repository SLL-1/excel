package com.example;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.IOException;
/**
 * 从excel表格中读取数据
 */
public class PoiReadExcel {
    public static void main(String[] args) {
        //获取数据的表格地址
        File file=new File("C:/Users/DELL/Desktop/poi_test.xls");
        try {
            //获取文件
            HSSFWorkbook workbook= new HSSFWorkbook(FileUtils.openInputStream(file));
            //根据工作簿的名称来获取
            HSSFSheet sheet=workbook.getSheet("Sheet0");
            //根据下标来获取，获取第一个工作簿
            //HSSFSheet sheet=workbook.getSheetAt(0);
            for (int i=sheet.getFirstRowNum();i<=sheet.getLastRowNum();i++){
                HSSFRow row=sheet.getRow(i);
                for(int j=row.getFirstCellNum();j<row.getLastCellNum();j++){
                    System.out.print(row.getCell(j)+"  ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
