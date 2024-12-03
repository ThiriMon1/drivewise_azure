package edu.miu.cse.drivewise.dto.response;

import java.util.List;

public record FavoriteResponse(
        Long inventoryId,
        String make,
        String model,
        Integer year,
        Integer mileage,
        String city,
        String state,
        double previousPrice,
        double currentPrice,
        String status,
        List<String> photos

) {
}
