package com.example.service;
import com.example.entity.SelectTemplate;
import com.example.entity.student;

import java.util.List;

public interface StudentService {
    List<student> getStudentList(SelectTemplate selectTemplate);
    int getStudentTotal (SelectTemplate selectTemplate);

}