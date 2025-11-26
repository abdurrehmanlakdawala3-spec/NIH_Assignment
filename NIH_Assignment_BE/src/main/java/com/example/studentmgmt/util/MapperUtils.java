package com.example.studentmgmt.util;

import com.example.studentmgmt.dto.response.CourseResponse;
import com.example.studentmgmt.dto.response.EnrollmentViewResponse;
import com.example.studentmgmt.dto.response.StudentResponse;
import com.example.studentmgmt.entity.Course;
import com.example.studentmgmt.entity.Enrollment;
import com.example.studentmgmt.entity.Student;

public final class MapperUtils {

    private MapperUtils() {}

    public static StudentResponse toStudentResponse(Student s) {
        if (s == null) return null;
        return StudentResponse.builder()
                .id(s.getId())
                .firstName(s.getFirstName())
                .lastName(s.getLastName())
                .email(s.getEmail())
                .dateOfBirth(s.getDateOfBirth())
                .build();
    }

    public static CourseResponse toCourseResponse(Course c) {
        if (c == null) return null;
        return CourseResponse.builder()
                .id(c.getId())
                .courseCode(c.getCourseCode())
                .title(c.getTitle())
                .description(c.getDescription())
                .build();
    }

    public static EnrollmentViewResponse toEnrollmentViewResponse(Enrollment e) {
        if (e == null) return null;
        String studentFullName = e.getStudent().getFirstName() + " " + e.getStudent().getLastName();
        Course course = e.getCourse();

        return EnrollmentViewResponse.builder()
                .id(e.getId())
                .studentFullName(studentFullName)
                .courseCode(course.getCourseCode())
                .courseTitle(course.getTitle())
                .build();
    }
}
