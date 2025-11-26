package com.example.studentmgmt.dto.request;

import com.example.studentmgmt.validation.CourseCodeFormat;
import com.example.studentmgmt.validation.UniqueCourseCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseCreateRequest {
    @NotBlank
    @CourseCodeFormat
    @UniqueCourseCode
    private String courseCode;

    @NotBlank
    @Size(max = 255)
    private String title;

    private String description;
}
