package com.example;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * 根据表格xml模板创建表格
 */
public class CreateExcelTemplate {
    public static void main(String[] args) {
       //文件路径
        String path=System.getProperty("user.dir")+"/src/main/java/student.xml";
        File file=new File(path);
        //创建excel表格
        HSSFWorkbook workbook=new HSSFWorkbook();
        //创建工作簿
        HSSFSheet sheet=workbook.createSheet();
        //当前创建表格的行号
         int rownum=0;
        SAXBuilder builder=new SAXBuilder();
        try {
            //解析xml文件
            Document document=builder.build(file);
            //获取xml文件的根节点
            Element root=document.getRootElement();
            Element colgroup=root.getChild("colgroup");
            //设置列宽
            setColumnWidth(sheet,colgroup);
            //获取表格的标题，表格第一行并合并
            Element title=root.getChild("title");
            rownum=setExcelTitle(sheet,title,rownum);
            //设置表头
            Element thead=root.getChild("thead");
            rownum=setExcelThead(sheet,thead,rownum);
            //设置单元格样式
            Element tbody=root.getChild("tbody");
            rownum=setExcelTbody(workbook,sheet,tbody,rownum);

            //输出表格
            File templatefile=new File("C:/Users/DELL/Desktop/template_test.xls");
            templatefile.createNewFile();
            FileOutputStream stream= FileUtils.openOutputStream(templatefile);
            workbook.write(stream);
            stream.close();
            workbook.close();
        }  catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     *  设置单元格样式
     */
    private static int setExcelTbody(HSSFWorkbook workbook, HSSFSheet sheet, Element tbody,int rownum) {
        Element tr=tbody.getChild("tr");
        try {
            int repeat=tr.getAttribute("repeat").getIntValue();
            for (int j=0;j<repeat;j++) {
                HSSFRow hssfRow = sheet.createRow(rownum);
                List<Element> tds = tr.getChildren("td");
                for (int i = 0; i < tds.size(); i++) {
                    Element td = tds.get(i);
                    String type = td.getAttributeValue("type");
                    HSSFCell hssfCell=hssfRow.createCell(i);
                    HSSFCellStyle hssfCellStyle=workbook.createCellStyle();
                    HSSFDataFormat dataFormat=workbook.createDataFormat();
                    if(type.equalsIgnoreCase("NUMERIC")){
                        hssfCell.setCellType(CellType.NUMERIC);
                        String format = td.getAttributeValue("format");
                        format=StringUtils.isNoneBlank(format)?format:"#,##0.000";
                        hssfCellStyle.setDataFormat(dataFormat.getFormat(format));

                    }else if(type.equalsIgnoreCase("STRING")){
                        hssfCell.setCellType(CellType.STRING);
                        hssfCellStyle.setDataFormat(dataFormat.getFormat("@"));

                    }else if(type.equalsIgnoreCase("DATE")) {
                        hssfCell.setCellType(CellType.NUMERIC);
                        hssfCellStyle.setDataFormat(dataFormat.getFormat("yyyy-m-d"));
                    }else if(type.equalsIgnoreCase("ENUM")) {
                        String format = td.getAttributeValue("format");
                        //加载下拉列表内容
                        DVConstraint constraint=DVConstraint.createExplicitListConstraint(format.split(","));
                        //下拉列表的表格位置
                        CellRangeAddressList rangeAddressList=new CellRangeAddressList(hssfCell.getRowIndex(),hssfCell.getRowIndex(),hssfCell.getColumnIndex(),hssfCell.getColumnIndex());
                        HSSFDataValidation dataValidation=new HSSFDataValidation(rangeAddressList,constraint);
                        workbook.getSheetAt(0).addValidationData(dataValidation);
                    }
                    hssfCell.setCellStyle(hssfCellStyle);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  rownum;
    }

    /**
     * 设置表头
     */
    private static int setExcelThead(HSSFSheet sheet, Element thead,int rownum) {
        List<Element> trs=thead.getChildren("tr");
        for(int i=0;i<trs.size();i++){
            Element tr=trs.get(i);
            List<Element> ths=tr.getChildren("th");
            HSSFRow hssfRow=sheet.createRow(rownum);
            for (int j=0;j<ths.size();j++){
                Element th=ths.get(j);
                Attribute value=th.getAttribute("value");
                HSSFCell hssfCell=hssfRow.createCell(j);
                hssfCell.setCellValue(value.getValue());
            }
            rownum++;
        }
        return rownum;
    }

    /**
     * 设置表格的标题
     */
    private static int setExcelTitle(HSSFSheet sheet, Element title,int rownum) {
        List<Element> trs=title.getChildren("tr");
        for (int i=0;i<trs.size();i++){
            HSSFRow hssfRow=sheet.createRow(rownum);
            Element tr=trs.get(i);
            List<Element> tds=tr.getChildren("td");
            for (int j=0;j<tds.size();){
                Element td=tds.get(j);
                HSSFCell cell=hssfRow.createCell(j);
                Attribute rowspan=td.getAttribute("rowspan");
                Attribute colspan=td.getAttribute("colspan");
                Attribute value=td.getAttribute("value");
                if (value!=null){
                    String val=value.getValue();
                    cell.setCellValue(val);
                    try {
                        int rspan=rowspan.getIntValue();
                        int cspan=colspan.getIntValue();
                        //合并单元格并居中
                        sheet.addMergedRegion(new CellRangeAddress(rownum,rownum+rspan-1,j,j+cspan-1));
                        j=j+cspan;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            rownum++;
        }
        return  rownum;
    }

    /**
设置列宽
 */
    private static void setColumnWidth(HSSFSheet sheet, Element colgroup) {
        List<Element> cols=colgroup.getChildren("col");
        for (int i=0;i<cols.size();i++){
            Element col=cols.get(i);
            Attribute width=col.getAttribute("width");
            //去掉.和数字
            String unit=width.getValue().replaceAll("[0-9,\\.]","");
            String value=width.getValue().replaceAll(unit,"");
            //v值是根据列宽的单位进行excel表格中的列宽大小进行转换
            int v=0;
            if(StringUtils.isBlank(unit)||"px".endsWith(unit)){
                v=Math.round(Float.parseFloat(value)*37f);
            }else if(unit.endsWith("em")){
                v=Math.round(Float.parseFloat(value)*267.5f);
            }
            sheet.setColumnWidth(i,v);
        }
    }


}
