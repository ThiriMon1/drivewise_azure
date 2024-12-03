package edu.miu.cse.drivewise.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MakeRequestDto(
        @NotBlank(message = "blank - null - empty are not accepted")
        String makeName
        ) {
}
