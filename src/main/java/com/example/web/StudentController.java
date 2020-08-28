package com.example.web;

import com.example.entity.SelectTemplate;
import com.example.entity.student;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

//    @RequestMapping(value = "/student-list",method=RequestMethod.POST)
//    private Map<String ,Object> getStudentList( @RequestParam String page,@RequestParam String rows){
//        Map<String,Object> modelMap=new HashMap<String, Object>();
//        modelMap.put("student",studentService.getStudentList());
//        System.out.println(modelMap);
//        return modelMap;
//
//    }
@RequestMapping(value = "/student-list",method=RequestMethod.POST)
private Map<String,Object> getStudentList(SelectTemplate selectTemplate){
//    sort：排序列字段名。
//    order：排序方式，可以是 'asc' 或者 'desc'，默认值是 'asc'。
//    page：页码，起始值 1。
//    rows：每页显示行。
    Map<String,Object> modelMap=new HashMap<String, Object>();
    modelMap.put("rows",studentService.getStudentList(selectTemplate));
    modelMap.put("total",studentService.getStudentTotal(selectTemplate));
    return modelMap;
}
}
