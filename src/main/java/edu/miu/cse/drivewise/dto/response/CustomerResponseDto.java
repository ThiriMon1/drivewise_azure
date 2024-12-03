package edu.miu.cse.drivewise.dto.response;

public record CustomerResponseDto(
        Long userId,
        String firstName,
        String lastName,
        String email

) {
}
