package edu.miu.cse.drivewise.service.impl;

import edu.miu.cse.drivewise.dto.request.CarCriteriaRequest;
import edu.miu.cse.drivewise.dto.request.InventoryRequestDto;
import edu.miu.cse.drivewise.dto.response.InventoryResponseDto;
import edu.miu.cse.drivewise.exception.resourcenotfound.ResourceNotFoundException;
import edu.miu.cse.drivewise.exception.carcondition.CarConditionNotFoundException;
import edu.miu.cse.drivewise.exception.offer.InventoryIsNotAvailableException;
import edu.miu.cse.drivewise.mapper.InventoryMapper;
import edu.miu.cse.drivewise.model.*;
import edu.miu.cse.drivewise.repository.*;
import edu.miu.cse.drivewise.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;
    private final ModelRepository modelRepository;

    private final InventoryMapper inventoryMapper;
    private final MakeRepository makeRepository;
    private final CarDao carDao;

    @Override
    public Optional<InventoryResponseDto> registerInventory(InventoryRequestDto requestDto, List<String> imageUrls) {

        Inventory inventory =inventoryMapper.inventoryRequestDtoToInventory(requestDto);

        inventory.setMake(getMake(inventory.getMake().getMakeName()));
        inventory.setModel(getModel(inventory.getModel().getModelName()));
        inventory.setCity(getCity(inventory.getCity().getCityName()));
        inventory.setState(getState(inventory.getState().getStateName()));
        if(inventory.getSellerType().equals(SellerType.DEALER)){
            Dealer dealer = inventory.getDealerInfo();
            Address dealerAddress = dealer.getAddress();

            dealerAddress.setCity(getCity(dealerAddress.getCity().getCityName()));
            dealerAddress.setState(getState(dealerAddress.getState().getStateName()));
            dealer.setAddress(dealerAddress);
            inventory.setDealerInfo(dealer);
        }
        inventory.setPhotos(imageUrls);

        Inventory savedInventory = inventoryRepository.save(inventory);

        return Optional.of(inventoryMapper.inventoryToInventoryResponseDto(savedInventory));
    }

    @Override
    public Optional<InventoryResponseDto> updateInventory(Long inventoryId,InventoryRequestDto requestDto, List<String> imageUrls) {
        Inventory foundInventory = findInventory(inventoryId);

        if(foundInventory.getStatus().equals(Status.AVAILABLE)){
            foundInventory =inventoryMapper.inventoryRequestDtoToInventory(requestDto);
            foundInventory.setId(inventoryId);
            foundInventory.setMake(getMake(foundInventory.getMake().getMakeName()));
            foundInventory.setModel(getModel(foundInventory.getModel().getModelName()));
            foundInventory.setCity(getCity(foundInventory.getCity().getCityName()));
            foundInventory.setState(getState(foundInventory.getState().getStateName()));
            if(foundInventory.getSellerType().equals(SellerType.PRIVATE)){
                foundInventory.setDealerInfo(null);
            }
            if(foundInventory.getSellerType().equals(SellerType.DEALER)){
                Dealer dealer = foundInventory.getDealerInfo();
                Address dealerAddress = dealer.getAddress();

                dealerAddress.setCity(getCity(dealerAddress.getCity().getCityName()));
                dealerAddress.setState(getState(dealerAddress.getState().getStateName()));
                dealer.setAddress(dealerAddress);
                foundInventory.setDealerInfo(dealer);
            }

            foundInventory.setPhotos(imageUrls);

            Inventory updatedInventory = inventoryRepository.save(foundInventory);

            return Optional.of(inventoryMapper.inventoryToInventoryResponseDto(updatedInventory));
        }else{
            throw new InventoryIsNotAvailableException(inventoryId+"is not available");
        }

    }

    @Override
    public void deleteInventory(Long inventoryId) {
        Inventory inventory=findInventory(inventoryId);
        if(inventory.getStatus().equals(Status.AVAILABLE)){
            inventoryRepository.delete(inventory);
        }
    }

    @Override
    public List<InventoryResponseDto> getAllInventories() {
        List<Inventory> inventories = inventoryRepository.findInventoryByStatus(Status.AVAILABLE);
        List<InventoryResponseDto> inventoryResponseDtos = new ArrayList<>();
        for (Inventory inventory : inventories) {
            inventoryResponseDtos.add(inventoryMapper.inventoryToInventoryResponseDto(inventory));
        }
        return inventoryResponseDtos;
    }

    @Override
    public List<InventoryResponseDto> getAllInventoriesWithFilterCar(String make, String model,String stateName,String carCondition,String style
            ,Integer minMileage,Integer maxMileage,Double minPrice,Double maxPrice,Integer minYear,Integer maxYear,String fuelType) {

        CarCriteriaRequest carCriteriaRequest=new CarCriteriaRequest(make,model,stateName,carCondition,style,
                minMileage,maxMileage,minPrice,maxPrice,minYear,maxYear,fuelType);

        List<Inventory> inventories = carDao.findAllByCriteria(carCriteriaRequest);
        // Sort the list by price in ascending order
//        inventories.sort(Comparator.comparingDouble(Inventory::getCurrentPrice));
        // Create a mutable copy of the list before sorting
        List<Inventory> mutableInventories = new ArrayList<>(inventories);
        // Sort the mutable list by price in ascending order
        mutableInventories.sort(Comparator.comparingDouble(Inventory::getCurrentPrice));
        List<InventoryResponseDto> inventoryResponseDtos = new ArrayList<>();
        for (Inventory inventory : mutableInventories) {
            inventoryResponseDtos.add(inventoryMapper.inventoryToInventoryResponseDto(inventory));
        }
        return inventoryResponseDtos;
    }

    @Override
    public List<InventoryResponseDto> getInventoriesByCarCondition(String carCondition) {
        CarCondition condition = null;
        switch (carCondition) {
            case "NEW":
                condition=CarCondition.NEW;
                break;
            case "USED":
                condition=CarCondition.USED;
                break;
                default:
                    throw new CarConditionNotFoundException("Invalid car condition: " + carCondition);
        }

        List<Inventory> inventories = inventoryRepository.findByCarCondition(condition);
        List<InventoryResponseDto>  inventoryResponseDtos = new ArrayList<>();
        for (Inventory inventory : inventories) {
            inventoryResponseDtos.add(inventoryMapper.inventoryToInventoryResponseDto(inventory));
        }
        return inventoryResponseDtos;
    }

    @Override
    public Optional<InventoryResponseDto> getInventory(Long inventoryId) {
        Inventory inventory=inventoryRepository.findById(inventoryId).orElseThrow(
                ()-> new ResourceNotFoundException(inventoryId+"is not found"));
        return Optional.of(inventoryMapper.inventoryToInventoryResponseDto(inventory));
    }

    private Inventory findInventory(Long inventoryId) {
        return inventoryRepository.findById(inventoryId).orElseThrow(()->new ResourceNotFoundException(inventoryId+" is not found"));
    }

    private Make getMake(String makeName) {
        return makeRepository.findMakeByMakeName(makeName).orElseThrow(()->new ResourceNotFoundException(makeName+" is not found"));
    }

    private CarModel getModel(String modelName) {
        return modelRepository.getModelByModelName(modelName).orElseThrow(()->new ResourceNotFoundException(modelName+" is not found"));
    }

    private City getCity(String cityName){
        return cityRepository.findCityByCityName(cityName).orElseThrow(()->new ResourceNotFoundException(cityName+" is not found"));
    }

    private State getState(String stateName) {
        return stateRepository.findStateByStateName(stateName).orElseThrow(()->new ResourceNotFoundException(stateName+" is not found"));
    }


}
