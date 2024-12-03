package edu.miu.cse.drivewise.dto.response;

import edu.miu.cse.drivewise.model.CarModel;
import edu.miu.cse.drivewise.model.Customer;
import edu.miu.cse.drivewise.model.Make;

import java.time.LocalDateTime;

public record CarReviewResponseDto(
        Long reviewId,
        String content,
        int star,
        CustomerResponseDto customer,
        MakeResponseDto make,
        CarModelResponseDto carModel,
        int year,
        LocalDateTime createdAt

) {
}
