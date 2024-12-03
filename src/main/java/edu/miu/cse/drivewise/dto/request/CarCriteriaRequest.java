package edu.miu.cse.drivewise.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarCriteriaRequest{
    private String make;
    private String model;
    private String stateName;
    private String carCondition;
    private String style;
    private Integer minMileage;
    private Integer maxMileage;
    private Double minPrice;
    private Double maxPrice;
    private Integer minYear;
    private Integer maxYear;
    private String fuelType;
}
