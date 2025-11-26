package com.example.studentmgmt.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentCreateRequest {
    @NotNull
    private Long studentId;

    @NotNull
    private Long courseId;
}
