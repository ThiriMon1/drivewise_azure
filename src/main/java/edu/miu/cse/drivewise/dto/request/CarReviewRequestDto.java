package edu.miu.cse.drivewise.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CarReviewRequestDto(
        @NotBlank(message = "blank/null/empty not allowed")
        String content,
        @NotBlank(message = "blank/null/empty not allowed")
        Integer star,
        @NotNull
        MakeRequestDto make,
        @NotNull
        CarModelRequestDto carModel,
        @NotBlank(message = "blank/null/empty not allowed")
        Integer year,
        @NotNull
        CustomerRequestDto customer
) {
}
