package edu.miu.cse.drivewise.dto.request;

import java.time.LocalDate;

public record PriceHistoryRequestDto(
        LocalDate date,
        double price
) {
}
