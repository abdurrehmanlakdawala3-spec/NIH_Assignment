package com.example.studentmgmt.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentViewResponse {
    private Long id;
    private String studentFullName;
    private String courseCode;
    private String courseTitle;
}
