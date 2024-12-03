package edu.miu.cse.drivewise.dto.response;

import edu.miu.cse.drivewise.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record InventoryResponseDto(
        Long id,
        MakeResponseDto make,
        CarModelResponseDto model,
        Integer year,
        double currentPrice,
        Integer mileage,
        CityResponseDto city,
        StateResponseDto state,
        SellerType sellerType,
        String color,
        String transmission,
        String VIN,
        String engineType,
        String drivetrain,
        FuelType fuelType,
        int mpgCity,
        int mpgHighway,
        String interiorMaterial,
        int numberOfSeats,
        String bodyType,
        CarCondition carCondition,
        String previousOwners,
        String accidentHistory,
        String warranty,
        List<String> features,
        String trimLevel,
        DealerResponseDto dealerInfo,
        List<PriceHistoryResponseDto> priceHistory,
        LocalDate listingDate,
        List<String> photos,
        double rating,
        Status status,
        String inspectionDetails,
        LocalDate saleDate,
        LocalDateTime createDateTime,
        LocalDateTime updateDateTime
) {
}
