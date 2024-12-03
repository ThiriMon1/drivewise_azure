package edu.miu.cse.drivewise.dto.request;

import edu.miu.cse.drivewise.model.Address;
import jakarta.validation.constraints.NotBlank;

public record DealerRequestDto(
        Integer id,
        @NotBlank(message = "blank/null/empty not allowed")
        String dealerName,
        @NotBlank(message = "blank/null/empty not allowed")
        String phone,
        Address address,
        String rating
) {
}
