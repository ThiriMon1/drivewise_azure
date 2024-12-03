package edu.miu.cse.drivewise.dto.response;

public record OfferCustomerResponseDto(
        Long userId,
        String firstName,
        String lastName,
        String email,
        String phone
) {
}
