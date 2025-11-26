package com.example.studentmgmt.service.impl;

import com.example.studentmgmt.dto.request.StudentCreateRequest;
import com.example.studentmgmt.dto.request.StudentUpdateRequest;
import com.example.studentmgmt.dto.response.StudentResponse;
import com.example.studentmgmt.entity.Student;
import com.example.studentmgmt.exception.ConflictException;
import com.example.studentmgmt.exception.NotFoundException;
import com.example.studentmgmt.repository.EnrollmentRepository;
import com.example.studentmgmt.repository.StudentRepository;
import com.example.studentmgmt.service.StudentService;
import com.example.studentmgmt.util.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    @Transactional
    public StudentResponse createStudent(StudentCreateRequest request) {
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already exists: " + request.getEmail());
        }
        Student s = Student.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .dateOfBirth(request.getDateOfBirth())
                .build();
        Student saved = studentRepository.save(s);
        return MapperUtils.toStudentResponse(saved);
    }

    @Override
    @Transactional
    public StudentResponse updateStudent(Long id, StudentUpdateRequest request) {
        Student s = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found with id=" + id));
        if (request.getEmail() != null && !request.getEmail().equalsIgnoreCase(s.getEmail()) && studentRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already exists: " + request.getEmail());
        }
        if (request.getFirstName() != null) s.setFirstName(request.getFirstName());
        if (request.getLastName() != null) s.setLastName(request.getLastName());
        if (request.getEmail() != null) s.setEmail(request.getEmail());
        if (request.getDateOfBirth() != null) s.setDateOfBirth(request.getDateOfBirth());
        s.setUpdatedAt(java.time.OffsetDateTime.now());
        Student saved = studentRepository.save(s);
        return MapperUtils.toStudentResponse(saved);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        Student s = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found with id=" + id));
        if (s.getEnrollments() != null && !s.getEnrollments().isEmpty()) {
            throw new ConflictException("Cannot delete student with active enrollments. Id=" + id);
        }
        studentRepository.delete(s);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Long id) {
        Student s = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found with id=" + id));
        return MapperUtils.toStudentResponse(s);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentResponse> listStudents(Pageable pageable) {
        Page<Student> page = studentRepository.findAll(pageable);
        return page.map(MapperUtils::toStudentResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentResponse> search(String searchString, Pageable pageable) {
        Page<Student> page = studentRepository.findByLastNameOrFirstNameOrEmailIgnoreCase(searchString, pageable);
        return page.map(MapperUtils::toStudentResponse);
    }
}
