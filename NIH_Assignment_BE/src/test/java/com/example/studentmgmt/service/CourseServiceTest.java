package com.example.studentmgmt.service;

import com.example.studentmgmt.dto.request.CourseCreateRequest;
import com.example.studentmgmt.entity.Course;
import com.example.studentmgmt.exception.ConflictException;
import com.example.studentmgmt.repository.CourseRepository;
import com.example.studentmgmt.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    private CourseService courseService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        courseService = new CourseServiceImpl(courseRepository);
    }

    @Test
    void createCourse_success() {
        CourseCreateRequest req = CourseCreateRequest.builder()
                .courseCode("CS-101")
                .title("Intro")
                .description("desc")
                .build();

        when(courseRepository.existsByCourseCodeIgnoreCase("CS-101")).thenReturn(false);
        when(courseRepository.save(any(Course.class))).thenAnswer(invocation -> {
            Course c = invocation.getArgument(0);
            c.setId(1L);
            return c;
        });

        var resp = courseService.createCourse(req);
        assertNotNull(resp);
        assertEquals("CS-101", resp.getCourseCode());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void createCourse_duplicateCode_throwsConflict() {
        CourseCreateRequest req = CourseCreateRequest.builder()
                .courseCode("CS-101")
                .title("Intro")
                .build();

        when(courseRepository.existsByCourseCodeIgnoreCase("CS-101")).thenReturn(true);
        assertThrows(ConflictException.class, () -> courseService.createCourse(req));
    }
}
