package com.example.web;

import com.example.DemoApplication;
import com.example.config.dao.SpringContextUtil;
import com.example.entity.ExportFormTemplate;
import com.example.entity.SelectTemplate;
import com.example.service.StudentService;
import com.example.service.impl.StudentServiceImpl;
import com.example.utils.ExportUtils;
import javafx.application.Application;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.eclipse.jetty.server.ServletResponseHttpWrapper;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.beans.beancontext.BeanContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@RestController
public class baseController {

@RequestMapping(value = "/export",method=RequestMethod.POST)
private  void exportExcel(HttpServletResponse respons,ExportFormTemplate exportFormTemplate){
    respons.setContentType("applayion/octet-stream");
    respons.setHeader("Content-Disposition","attachment;filename=export.xls");
    //创建excel表格
    HSSFWorkbook hssfWorkbook=new HSSFWorkbook();
    //创建工作簿
    HSSFSheet sheet=hssfWorkbook.createSheet();
    try {
        String className= exportFormTemplate.getClassName();
        className=className.substring(0,1).toLowerCase()+className.substring(1);
        Object object = SpringContextUtil.getBean(className);
        Method method = ReflectionUtils.findMethod(object.getClass(), exportFormTemplate.getMethodName());

        List  list=(List )ReflectionUtils.invokeMethod(method, object);

        ExportUtils.outputHeaders(sheet,exportFormTemplate.getTitles().split(","));
        ExportUtils.outputColumns(sheet,list,exportFormTemplate.getFields().split(","),1);
        ServletOutputStream outputStream=respons.getOutputStream();
        hssfWorkbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
