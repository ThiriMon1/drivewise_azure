package edu.miu.cse.drivewise.dto.response;

import edu.miu.cse.drivewise.model.City;
import edu.miu.cse.drivewise.model.State;
import jakarta.persistence.ManyToOne;

public record AddressResponseDto(
        String number,
        String street,
        String zipCode,
        CityResponseDto city,
        StateResponseDto state
) {
}
