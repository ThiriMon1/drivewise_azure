package edu.miu.cse.drivewise.dto.response;

import edu.miu.cse.drivewise.model.Inventory;
import edu.miu.cse.drivewise.model.OfferStatus;
import edu.miu.cse.drivewise.model.Status;

import java.util.List;

public record OfferResponseDto(
        Long offerId,
    OfferCustomerResponseDto customerResponseDto,
    Long inventoryId,
    String make,
    String model,
    Integer year,
    Integer mileage,
    String city,
    String state,
    double currentPrice,
    Status status,
    List<String> photos,
    OfferStatus offerStatus,
    double offerPrice,
    boolean cancel_flag

) {
}
