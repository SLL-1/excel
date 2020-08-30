package com.example.utils;

import com.example.entity.student;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.lang.reflect.Method;
import java.util.List;

public class ExportUtils {
    public  static void outputHeaders(HSSFSheet sheet,String[] titles){
        //创建工作簿的第一行
        HSSFRow hssfRow=sheet.createRow(0);
        HSSFCell cell;
        //填写第一行数据
        for (int i=0;i<titles.length;i++){
            cell=hssfRow.createCell(i);
            cell.setCellValue(titles[i]);
        }
    }
    public static void outputColumns(HSSFSheet sheet, List<Object> objs,String[] columnsId,int rowIndex) {
        HSSFRow hssfRow;
        for (int i=0;i<objs.size();i++){
            hssfRow=sheet.createRow(rowIndex++);
            Object obj=objs.get(i);
            for (int j=0;j<columnsId.length;j++){
                HSSFCell cell=hssfRow.createCell(j);
                Object value=getFieldValueByName(columnsId[j],obj);
                cell.setCellValue(value.toString());
            }
        }
    }

    private static Object getFieldValueByName(String columnsId, Object obj) {
        String methodName="get"+columnsId.substring(0,1).toUpperCase()+columnsId.substring(1);
        try {
            Method method=obj.getClass().getMethod(methodName,new Class[]{});
            Object value=method.invoke(obj,new Object[]{});
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
