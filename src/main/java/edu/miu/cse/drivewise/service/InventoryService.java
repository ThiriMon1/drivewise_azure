package edu.miu.cse.drivewise.service;

import edu.miu.cse.drivewise.dto.request.InventoryRequestDto;
import edu.miu.cse.drivewise.dto.response.InventoryResponseDto;

import java.util.List;
import java.util.Optional;

public interface InventoryService {
    Optional<InventoryResponseDto> registerInventory(InventoryRequestDto requestDto, List<String> imageUrls);
    Optional<InventoryResponseDto> updateInventory(Long inventoryId, InventoryRequestDto requestDto, List<String> imageUrls);
    Optional<InventoryResponseDto> getInventory(Long id);
    void deleteInventory(Long inventoryId);
    List<InventoryResponseDto> getAllInventories();
    List<InventoryResponseDto> getAllInventoriesWithFilterCar(String make, String model,String stateName,String carCondition,String style
            ,Integer minMileage,Integer maxMileage,Double minPrice,Double maxPrice,Integer minYear,Integer maxYear,String fuelType);
    List<InventoryResponseDto> getInventoriesByCarCondition(String carCondition);

}
