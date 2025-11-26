package com.example.studentmgmt.repository;

import com.example.studentmgmt.entity.Course;
import com.example.studentmgmt.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByCourseCodeIgnoreCase(String courseCode);
    Optional<Course> findByCourseCodeIgnoreCase(String courseCode);

    @Query("SELECT c FROM Course c WHERE LOWER(c.courseCode) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(c.title) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Course> searchByCourseCodeOrTitle(@Param("searchTerm") String searchTerm, Pageable pageable);
}
