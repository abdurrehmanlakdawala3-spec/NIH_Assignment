package com.example.studentmgmt.controller;

import com.example.studentmgmt.dto.request.EnrollmentCreateRequest;
import com.example.studentmgmt.dto.response.EnrollmentViewResponse;
import com.example.studentmgmt.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<Page<EnrollmentViewResponse>> listEnrollments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false, defaultValue = "") String searchText) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EnrollmentViewResponse> pageResp = enrollmentService.listEnrollments(pageable, searchText);
        return ResponseEntity.ok(pageResp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentViewResponse> getEnrollment(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.getEnrollment(id));
    }

    @PostMapping
    public ResponseEntity<EnrollmentViewResponse> createEnrollment(
            @Validated @RequestBody EnrollmentCreateRequest request) {
        EnrollmentViewResponse resp = enrollmentService.createEnrollment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }

}
