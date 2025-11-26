package com.example.studentmgmt.service;

import com.example.studentmgmt.dto.request.StudentCreateRequest;
import com.example.studentmgmt.dto.request.StudentUpdateRequest;
import com.example.studentmgmt.dto.response.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
    StudentResponse createStudent(StudentCreateRequest request);
    StudentResponse updateStudent(Long id, StudentUpdateRequest request);
    void deleteStudent(Long id);
    StudentResponse getStudentById(Long id);
    Page<StudentResponse> listStudents(Pageable pageable);
    Page<StudentResponse> search(String searchString, Pageable pageable);
}
