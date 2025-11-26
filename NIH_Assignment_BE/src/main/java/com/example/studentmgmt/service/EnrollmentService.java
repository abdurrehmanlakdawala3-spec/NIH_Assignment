package com.example.studentmgmt.service;

import com.example.studentmgmt.dto.request.EnrollmentCreateRequest;
import com.example.studentmgmt.dto.response.EnrollmentViewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EnrollmentService {
    EnrollmentViewResponse createEnrollment(EnrollmentCreateRequest request);
    EnrollmentViewResponse getEnrollment(Long id);
    void deleteEnrollment(Long id);
    Page<EnrollmentViewResponse> listEnrollments(Pageable pageable, Long studentId, Long courseId);
}
