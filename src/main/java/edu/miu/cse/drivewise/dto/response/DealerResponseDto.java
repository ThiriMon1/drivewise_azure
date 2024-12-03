package edu.miu.cse.drivewise.dto.response;

public record DealerResponseDto(
        Long id,
        String dealerName,
        String phone,
        AddressResponseDto address,
        String rating
) {
}
