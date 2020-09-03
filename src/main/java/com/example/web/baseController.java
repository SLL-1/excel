package com.example.web;

import com.example.config.dao.SpringContextUtil;
import com.example.entity.ExportFormTemplate;
import com.example.entity.student;
import com.example.entity.importdata;
import com.example.entity.importdatadetail;
import com.example.service.ImportDataDetailService;
import com.example.service.ImportDataService;
import com.example.service.StudentService;
import com.example.utils.DownExcelTemplateUtils;
import com.example.utils.ExportUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class baseController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private ImportDataService importDataService;
    @Autowired
    private ImportDataDetailService importDataDetailService;

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

@RequestMapping(value = "/downloadTemplate")
private void downloadTemplate(String templateId){
    DownExcelTemplateUtils.createExcelTemplate(templateId);
}
//导入excel表格数据
@RequestMapping(value = "/importExcel")
    private Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, String templateId){
    Map<String,Object> modelMap=new HashMap<String, Object>();
          if(file.isEmpty()){
//              return "上传失败，请重新上传";
              modelMap.put("status","fail");
              modelMap.put("message","上传失败，请重新上传");
          }else{
              String fileName=file.getOriginalFilename();
              try {
                  String path=System.getProperty("user.dir")+"/src/main/resources/excelTemplate/"+templateId;
                  File filepath = new File(path);
                  if (!filepath.exists()) {
                      filepath.mkdirs();
                  }
                  File dest=new File(path+File.separator+fileName);
                  //保存上传的表格
                  file.transferTo(dest);

                  //保存主表数据
                  importdata importdata=new importdata();
                  importdata.setType(templateId);
                  importdata.setStatus(1);
                  int importId=importDataService.insertImportData(importdata);
                  if(importId>0){
                      modelMap.put("status","success");
                      modelMap.put("message","上传成功");
                  }else{
                      modelMap.put("status","fail");
                      modelMap.put("message","上传失败，请重新上传");
                  }
                  //获取模板中的开始行数和开始列数
                  SAXBuilder builder=new SAXBuilder();
                  //解析xml文件
                  URI excelTemplatepath=new ClassPathResource("excelTemplate"+File.separator+templateId+".xml").getURI();
                  File  excelTemplatefile = new File(excelTemplatepath);
                  Document document=builder.build(excelTemplatefile);
                  //获取xml文件的根节点
                  Element root=document.getRootElement();
                  //设置单元格样式
                  Element tbody=root.getChild("tbody");
                  Element tr=tbody.getChild("tr");
                  List<Element> tds = tr.getChildren("td");
                  //获取模板中的开始行数和开始列数
                  int firstrow=tr.getAttribute("firstrow").getIntValue();
                  int firstcol=tr.getAttribute("firsrcol").getIntValue();

                  //获取文件
                  HSSFWorkbook workbook= new HSSFWorkbook(FileUtils.openInputStream(dest));
                  //根据下标来获取，获取第一个工作簿
                  HSSFSheet sheet=workbook.getSheetAt(0);

                  for (int i=firstrow;i<sheet.getLastRowNum()+1;i++){

                      //添加导入详细数据
                      importdatadetail importdatadetail=new importdatadetail();
                      importdatadetail.setStatus(0);
                      importdatadetail.setImportid(importId);

                      HSSFRow row=sheet.getRow(i);
                      List<Object> cellValue =new ArrayList<>();
                      if(isEmptyRow(row)){
                          continue;
                      }
                      for(int j=firstcol;j<row.getLastCellNum();j++){
                          Element td=tds.get(j);
                          HSSFCell cell=row.getCell(j);

                          if(cell==null){
                              continue;
                          }

                          String value=getCellValues(cell,td);
                          if(!StringUtils.isEmpty(value)){
                              if(value.indexOf("#000")>=0){
                                  String[] info=value.split(",");
                                  importdatadetail.setFailcode(info[0]);
                                  importdatadetail.setMsg(info[1]);
                                  BeanUtils.setProperty(importdatadetail,"col"+j,value);
                              }else {
                                  BeanUtils.setProperty(importdatadetail,"col"+j,value);
                              }
                          }
                      }
                      importDataDetailService.insertImportDataDetail(importdatadetail);


//                      int stunum=Integer.parseInt((String )cellValue.get(0));
//                      String stuname=(String )cellValue.get(1);
//                      int stuage=Integer.parseInt((String )cellValue.get(2));
//                      String stusex=(String )cellValue.get(3);
//                      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
//                      Date stubirthday = simpleDateFormat.parse((String)cellValue.get(4) );
//                      String stuhobby=(String )cellValue.get(5);
//                      int num=studentService.insertStudent(new student(stunum,stuname,stuage,stusex,stubirthday,stuhobby));
//                  if(num>0){
//                      System.out.println("上传成功");
//                  }else{
//                      System.out.println("上传失败");
//                  }
                      //导入具体数据

                  }
                  boolean flag=studentService.insertStudentByImportId(importId);
                  if(flag){
                      modelMap.put("status","success");
                      modelMap.put("message","上传成功");
                  }else{
                      modelMap.put("status","fail");
                      modelMap.put("message","上传失败，请重新上传");
                  }
              } catch (Exception e) {
                  e.printStackTrace();
                  modelMap.put("status","fail");
                  modelMap.put("message","上传失败，请重新上传");
              }

          }
return modelMap;
}

    private boolean isEmptyRow(HSSFRow row) {
    boolean flag=true;
    for(int i=0;i<row.getLastCellNum();i++){
        HSSFCell cell=row.getCell(i);
        if(cell!=null){
            if(!StringUtils.isEmpty(cell.toString()))
                flag=false;
        }
    }
    return flag;
    }

    private String getCellValues(HSSFCell cell, Element td) {
    int i=cell.getRowIndex()+1;
    int j=cell.getColumnIndex()+1;
    String returnValue="";//返回值
        try {
        String type=td.getAttributeValue("type");
            Boolean isNullAble=td.getAttribute("isnullable").getBooleanValue();
            int maxlength=9999;
            if(td.getAttribute("maxlength")!=null){
                maxlength=td.getAttribute("maxlength").getIntValue();
            }
            String value=null;
            switch (cell.getCellType()){
                case STRING:
                    value=cell.getStringCellValue();
                    break;
                case NUMERIC:{
                    if("datetime,date".indexOf(type)>=0){
                        Date date=cell.getDateCellValue();
                        SimpleDateFormat df=new SimpleDateFormat("yyyy-mm-dd");
                        value=df.format(date);
                    }else{
                        double numericvalue=cell.getNumericCellValue();
                        value=String.valueOf(numericvalue);
                    }
                    break;
                }
            }
            if(!isNullAble&& StringUtils.isEmpty(value)){
                returnValue="#0001,第"+i+"行第"+j+"列数据不能为空！,"+value;
            }else if (!StringUtils.isEmpty(value)&&(value.length()>maxlength)){
                returnValue="#0002,第"+i+"行第"+j+"列数据长度超过限制！,"+value;
            }else {
                returnValue=value;
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }
}
