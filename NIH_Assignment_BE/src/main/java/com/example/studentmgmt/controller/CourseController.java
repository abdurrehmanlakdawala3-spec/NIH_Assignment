package com.example.studentmgmt.controller;

import com.example.studentmgmt.dto.request.CourseCreateRequest;
import com.example.studentmgmt.dto.request.CourseUpdateRequest;
import com.example.studentmgmt.dto.response.CourseResponse;
import com.example.studentmgmt.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) { this.courseService = courseService; }

    @GetMapping
    public ResponseEntity<Page<CourseResponse>> listCourses(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(courseService.listCourses(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@Validated @RequestBody CourseCreateRequest request) {
        CourseResponse response = courseService.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable Long id, @Validated @RequestBody CourseUpdateRequest request) {
        CourseResponse response = courseService.updateCourse(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/validate-code")
    public ResponseEntity<Boolean> isCourseCodeAvailable(@RequestParam String courseCode) {
        boolean ok = courseService.isCourseCodeAvailable(courseCode);
        return ResponseEntity.ok(ok);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CourseResponse>> search(@RequestParam String searchString,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseResponse> result = courseService.search(searchString, pageable);
        return ResponseEntity.ok(result);
    }
}
