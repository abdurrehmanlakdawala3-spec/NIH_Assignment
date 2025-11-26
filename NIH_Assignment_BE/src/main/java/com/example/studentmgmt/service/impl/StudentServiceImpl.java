package com.example.studentmgmt.service.impl;

import com.example.studentmgmt.dto.request.StudentCreateRequest;
import com.example.studentmgmt.dto.request.StudentUpdateRequest;
import com.example.studentmgmt.dto.response.StudentResponse;
import com.example.studentmgmt.entity.Student;
import com.example.studentmgmt.entity.User;
import com.example.studentmgmt.enums.Role;
import com.example.studentmgmt.exception.ConflictException;
import com.example.studentmgmt.exception.NotFoundException;
import com.example.studentmgmt.repository.EnrollmentRepository;
import com.example.studentmgmt.repository.StudentRepository;
import com.example.studentmgmt.repository.UserRepository;
import com.example.studentmgmt.service.StudentService;
import com.example.studentmgmt.util.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    private static final String DEFAULT_PASSWORD = "password";

    @Autowired
    public StudentServiceImpl(
            StudentRepository studentRepository,
            EnrollmentRepository enrollmentRepository,
            UserRepository userRepository,
            PasswordEncoder encoder) {
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
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
        Optional<User> userExisting = userRepository.findByUsername(saved.getEmail());
        System.out.println(encoder.encode(DEFAULT_PASSWORD));
        System.out.println(encoder.encode(DEFAULT_PASSWORD));
        System.out.println(encoder.encode(DEFAULT_PASSWORD));
        System.out.println(encoder.encode(DEFAULT_PASSWORD));
        System.out.println(encoder.encode(DEFAULT_PASSWORD));
        if (userExisting.isEmpty()) {
            User user = User.builder()
                    .username(saved.getEmail())
                    .password(encoder.encode(DEFAULT_PASSWORD))
                    .role(Role.ROLE_STUDENT)
                    .studentId(saved.getId()).build();
            userRepository.save(user);
        } else {
            User existing = userExisting.get();
            existing.setStudentId(saved.getId());
            userRepository.save(existing);
        }
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
        if (s.getEmail() != null && !s.getEmail().equalsIgnoreCase(request.getEmail())) {
            userRepository.findByUsername(s.getEmail()).ifPresent(u -> {
                u.setUsername(request.getEmail());
                userRepository.save(u);
            });
        }
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
