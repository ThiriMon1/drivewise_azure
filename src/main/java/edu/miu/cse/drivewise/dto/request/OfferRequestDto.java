package edu.miu.cse.drivewise.dto.request;

import jakarta.validation.constraints.NotBlank;

public record OfferRequestDto(
        @NotBlank(message = "blank/null/empty not allowed")
        Long inventoryId,
        @NotBlank(message = "blank/null/empty not allowed")
        double offerPrice,
        @NotBlank(message = "blank/null/empty not allowed")
        String customerPhone
) {
}
