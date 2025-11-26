package com.example.studentmgmt.service;

import com.example.studentmgmt.dto.request.CourseCreateRequest;
import com.example.studentmgmt.dto.request.CourseUpdateRequest;
import com.example.studentmgmt.dto.response.CourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {
    CourseResponse createCourse(CourseCreateRequest request);
    CourseResponse updateCourse(Long id, CourseUpdateRequest request);
    void deleteCourse(Long id);
    CourseResponse getCourseById(Long id);
    Page<CourseResponse> listCourses(Pageable pageable);
    boolean isCourseCodeAvailable(String courseCode);
    Page<CourseResponse> search(String searchString, Pageable pageable);

}
