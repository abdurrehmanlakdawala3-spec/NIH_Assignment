package com.example.studentmgmt.repository;

import com.example.studentmgmt.entity.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Page<Enrollment> findByStudentId(Long studentId, Pageable pageable);

    Page<Enrollment> findByCourseId(Long courseId, Pageable pageable);

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    @Query(
            "SELECT e FROM Enrollment e " +
                    "WHERE LOWER(e.student.firstName) LIKE LOWER(CONCAT('%', :search, '%')) " +
                    "OR LOWER(e.student.lastName) LIKE LOWER(CONCAT('%', :search, '%')) " +
                    "OR LOWER(e.student.email) LIKE LOWER(CONCAT('%', :search, '%')) " +
                    "OR LOWER(e.course.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
                    "OR LOWER(e.course.courseCode) LIKE LOWER(CONCAT('%', :search, '%'))"
    )
    Page<Enrollment> searchEnrollments(@Param("search") String search, Pageable pageable);

    Page<Enrollment> findByStudent_Id(Long studentId, Pageable pageable);

}
