package com.example.studentmgmt.service.impl;

import com.example.studentmgmt.dto.request.CourseCreateRequest;
import com.example.studentmgmt.dto.request.CourseUpdateRequest;
import com.example.studentmgmt.dto.response.CourseResponse;
import com.example.studentmgmt.entity.Course;
import com.example.studentmgmt.exception.ConflictException;
import com.example.studentmgmt.exception.NotFoundException;
import com.example.studentmgmt.repository.CourseRepository;
import com.example.studentmgmt.service.CourseService;
import com.example.studentmgmt.util.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public CourseResponse createCourse(CourseCreateRequest request) {
        if (courseRepository.existsByCourseCodeIgnoreCase(request.getCourseCode())) {
            throw new ConflictException("Course code already exists: " + request.getCourseCode());
        }
        Course c = Course.builder()
                .courseCode(request.getCourseCode().toUpperCase())
                .title(request.getTitle())
                .description(request.getDescription())
                .build();
        Course saved = courseRepository.save(c);
        return MapperUtils.toCourseResponse(saved);
    }

    @Override
    @Transactional
    public CourseResponse updateCourse(Long id, CourseUpdateRequest request) {
        Course c = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Course not found with id=" + id));
        if (request.getTitle() != null) c.setTitle(request.getTitle());
        if (request.getDescription() != null) c.setDescription(request.getDescription());
        c.setUpdatedAt(java.time.OffsetDateTime.now());
        Course saved = courseRepository.save(c);
        return MapperUtils.toCourseResponse(saved);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        Course c = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Course not found with id=" + id));
        if (c.getEnrollments() != null && !c.getEnrollments().isEmpty()) {
            throw new ConflictException("Cannot delete course with enrollments. Id=" + id);
        }
        courseRepository.delete(c);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseResponse getCourseById(Long id) {
        Course c = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Course not found with id=" + id));
        return MapperUtils.toCourseResponse(c);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseResponse> listCourses(Pageable pageable) {
        Page<Course> page = courseRepository.findAll(pageable);
        return page.map(MapperUtils::toCourseResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isCourseCodeAvailable(String courseCode) {
        return !courseRepository.existsByCourseCodeIgnoreCase(courseCode);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseResponse> search(String searchString, Pageable pageable) {
        Page<Course> page = courseRepository.searchByCourseCodeOrTitle(searchString, pageable);
        return page.map(MapperUtils::toCourseResponse);
    }
}
