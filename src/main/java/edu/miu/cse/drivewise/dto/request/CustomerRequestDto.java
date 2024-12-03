package edu.miu.cse.drivewise.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CustomerRequestDto(
        @NotBlank(message = "blank/null/empty not allowed")
        String email
) {
}
