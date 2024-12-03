package edu.miu.cse.drivewise.dto.request;

import edu.miu.cse.drivewise.model.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public record InventoryRequestDto(
        Long id,
        @NotNull
        MakeRequestDto make,
        @NotNull
        CarModelRequestDto model,
        @NotNull
        Integer year,
        @NotNull
        double currentPrice,
        @NotNull
        Integer mileage,
        @NotNull
        City city,
        @NotNull
        State state,
        @NotNull
        SellerType sellerType,
        String color,
        String transmission,
        @NotBlank(message = "blank/null/empty not allowed")
        String VIN,
        String engineType,
        String drivetrain,
        FuelType fuelType,
        int mpgCity,
        int mpgHighway,
        String interiorMaterial,
        int numberOfSeats,
        String bodyType,
        @NotNull
        CarCondition carCondition,
        String previousOwners,
        String accidentHistory,
        String warranty,
        List<String> features,
        String trimLevel,
        DealerRequestDto dealerInfo,
        List<PriceHistoryRequestDto> priceHistory,
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
