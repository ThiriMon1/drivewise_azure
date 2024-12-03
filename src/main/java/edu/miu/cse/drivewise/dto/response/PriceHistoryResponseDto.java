package edu.miu.cse.drivewise.dto.response;

import java.time.LocalDate;

public record PriceHistoryResponseDto(
        LocalDate date,
        double price
) {
}
