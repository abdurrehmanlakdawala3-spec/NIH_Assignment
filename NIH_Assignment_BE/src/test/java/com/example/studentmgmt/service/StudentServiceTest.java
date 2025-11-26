package com.example.studentmgmt.service;

import com.example.studentmgmt.dto.request.StudentCreateRequest;
import com.example.studentmgmt.entity.Student;
import com.example.studentmgmt.exception.ConflictException;
import com.example.studentmgmt.repository.EnrollmentRepository;
import com.example.studentmgmt.repository.StudentRepository;
import com.example.studentmgmt.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    private StudentService studentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        studentService = new StudentServiceImpl(studentRepository, enrollmentRepository);
    }

    @Test
    void createStudent_duplicateEmail_throwsConflict() {
        StudentCreateRequest req = StudentCreateRequest.builder()
                .firstName("John").lastName("Smith").email("john@example.com")
                .build();

        when(studentRepository.existsByEmail("john@example.com")).thenReturn(true);

        assertThrows(ConflictException.class, () -> studentService.createStudent(req));
    }

    @Test
    void createStudent_success() {
        StudentCreateRequest req = StudentCreateRequest.builder()
                .firstName("John").lastName("Smith").email("john2@example.com")
                .build();

        when(studentRepository.existsByEmail("john2@example.com")).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> {
            Student s = invocation.getArgument(0);
            s.setId(1L);
            return s;
        });

        var resp = studentService.createStudent(req);
        assertNotNull(resp);
        assertEquals("John", resp.getFirstName());
    }
}
