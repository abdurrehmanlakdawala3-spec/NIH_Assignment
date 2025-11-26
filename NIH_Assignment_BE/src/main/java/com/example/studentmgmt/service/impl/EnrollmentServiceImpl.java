package com.example.studentmgmt.service.impl;

import com.example.studentmgmt.dto.request.EnrollmentCreateRequest;
import com.example.studentmgmt.dto.response.EnrollmentViewResponse;
import com.example.studentmgmt.entity.Course;
import com.example.studentmgmt.entity.Enrollment;
import com.example.studentmgmt.entity.Student;
import com.example.studentmgmt.exception.ConflictException;
import com.example.studentmgmt.exception.NotFoundException;
import com.example.studentmgmt.repository.CourseRepository;
import com.example.studentmgmt.repository.EnrollmentRepository;
import com.example.studentmgmt.repository.StudentRepository;
import com.example.studentmgmt.service.EnrollmentService;
import com.example.studentmgmt.util.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
                                 StudentRepository studentRepository,
                                 CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public EnrollmentViewResponse createEnrollment(EnrollmentCreateRequest request) {

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new NotFoundException("Student not found with id=" + request.getStudentId()));
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new NotFoundException("Course not found with id=" + request.getCourseId()));

        boolean exists = enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), course.getId());
        if (exists) {
            throw new ConflictException("Student already enrolled in the same course.");
        }

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .build();
        Enrollment saved = enrollmentRepository.save(enrollment);

        return MapperUtils.toEnrollmentViewResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public EnrollmentViewResponse getEnrollment(Long id) {
        Enrollment e = enrollmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Enrollment not found with id=" + id));
        return MapperUtils.toEnrollmentViewResponse(e);
    }

    @Override
    @Transactional
    public void deleteEnrollment(Long id) {
        Enrollment e = enrollmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Enrollment not found with id=" + id));
        enrollmentRepository.delete(e);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EnrollmentViewResponse> listEnrollments(Pageable pageable, String searchString) {
        Page<Enrollment> page = enrollmentRepository.searchEnrollments(searchString, pageable);
        return page.map(MapperUtils::toEnrollmentViewResponse);
    }
}
