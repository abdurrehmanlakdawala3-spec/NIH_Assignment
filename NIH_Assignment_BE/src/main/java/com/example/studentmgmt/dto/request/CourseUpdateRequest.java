package com.example.studentmgmt.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseUpdateRequest {
    @Size(max=255)
    private String title;

    private String description;
}
