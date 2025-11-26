package com.example.studentmgmt.controller;

import com.example.studentmgmt.dto.request.StudentCreateRequest;
import com.example.studentmgmt.dto.request.StudentUpdateRequest;
import com.example.studentmgmt.dto.response.StudentResponse;
import com.example.studentmgmt.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Page<StudentResponse>> listStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "lastName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sortBy, sortOrder)));
        Page<StudentResponse> result = studentService.listStudents(pageable);
        return ResponseEntity.ok(result);
    }

    private Sort.Order parseSort(String sortBy, String sortOrder) {
        if (null != sortBy) {
            Sort.Direction dir = null != sortOrder && sortOrder.equalsIgnoreCase("desc") ?
                    Sort.Direction.DESC : Sort.Direction.ASC;
            return new Sort.Order(dir, sortBy);
        }
        return new Sort.Order(Sort.Direction.ASC, "lastName");
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@Validated @RequestBody StudentCreateRequest request) {
        StudentResponse response = studentService.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable Long id, @Validated @RequestBody StudentUpdateRequest request) {
        StudentResponse response = studentService.updateStudent(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StudentResponse>> search(@RequestParam String searchString,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentResponse> result = studentService.search(searchString, pageable);
        return ResponseEntity.ok(result);
    }
}
