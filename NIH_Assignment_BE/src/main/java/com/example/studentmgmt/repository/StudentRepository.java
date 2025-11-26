package com.example.studentmgmt.repository;

import com.example.studentmgmt.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findByLastNameIgnoreCaseStartingWith(String prefix, Pageable pageable);

    @Query("SELECT s FROM Student s " +
            "WHERE LOWER(s.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(s.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(s.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Student> findByLastNameOrFirstNameOrEmailIgnoreCase(@Param("searchTerm") String searchTerm, Pageable pageable);

    boolean existsByEmail(String email);
}
