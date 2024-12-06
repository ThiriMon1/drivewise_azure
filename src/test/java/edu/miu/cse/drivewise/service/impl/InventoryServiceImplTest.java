package edu.miu.cse.drivewise.service.impl;

import edu.miu.cse.drivewise.dto.request.*;
import edu.miu.cse.drivewise.dto.response.*;
import edu.miu.cse.drivewise.exception.resourcenotfound.ResourceNotFoundException;
import edu.miu.cse.drivewise.mapper.InventoryMapper;
import edu.miu.cse.drivewise.model.*;
import edu.miu.cse.drivewise.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private StateRepository stateRepository;

    @Mock
    private ModelRepository modelRepository;

    @Mock
    private MakeRepository makeRepository;

    @Mock
    private CarDao carDao;

    @Mock
    private InventoryMapper inventoryMapper;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private Inventory inventory;
    private InventoryRequestDto inventoryRequestDto;
    private InventoryResponseDto inventoryResponseDto;
    private Customer customer;
    private Make make;
    private CarModel model;
    private City city;
    private State state;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setUserId(1L);

        make = new Make();
        model=new CarModel();
        model.setModelId(1L);
        model.setModelName("model");
        make.setMakeId(1L);
        make.setMakeName("Toyota");
        model.setMake(make);
        make.setModelList(List.of(model));

        city = new City();
        city.setCityName("Chicago");
        state=new State();
        state.setStateName("IL");

        List<PriceHistory> priceHistoryList = new ArrayList<>();
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setPrice(19000);
        priceHistoryList.add(priceHistory);

        inventory = new Inventory();
        inventory.setId(1L);
        inventory.setMake(make);
        inventory.setModel(model);
        inventory.setYear(2023);
        inventory.setCurrentPrice(19000);
        inventory.setMileage(100000);
        inventory.setCity(city);
        inventory.setState(state);
        inventory.setSellerType(SellerType.PRIVATE);
        inventory.setColor("Red");
        inventory.setTransmission("good");
        inventory.setVIN("werewf12432434");
        inventory.setEngineType("sdfdf");
        inventory.setFuelType(FuelType.GASOLINE);
        inventory.setMpgCity(40);
        inventory.setMpgHighway(30);
        inventory.setInteriorMaterial("good");
        inventory.setNumberOfSeats(7);
        inventory.setBodyType("good");
        inventory.setCarCondition(CarCondition.USED);
        inventory.setPreviousOwners("2");
        inventory.setWarranty("1year");
        inventory.setFeatures(List.of("good","used"));
        inventory.setTrimLevel("EL");
        inventory.setStatus(Status.AVAILABLE);

        inventoryRequestDto = new InventoryRequestDto(
                1L,
                new MakeRequestDto("Toyota"),
                new CarModelRequestDto("Camry"),
                2023,
                30000,
                10000,
                new City(),
                new State(),
                SellerType.DEALER,
                "Blue",
                "Automatic",
                "VIN12345",
                "V6",
                "AWD",
                FuelType.GASOLINE,
                20,
                30,
                "Leather",
                5,
                "Sedan",
                CarCondition.NEW,
                "None",
                "None",
                "3 years",
                List.of("Airbags", "Backup Camera"),
                "XLE",
                new DealerRequestDto(1, "dealer", "0132434234", new Address(), "4"),
                null,
                null,
                null,
                4,
                Status.AVAILABLE,
                "Inspected",
                null,
                null,
                null
        );

        inventoryResponseDto = new InventoryResponseDto(
                1L,
                new MakeResponseDto("Toyota"),
                new CarModelResponseDto("Camry"),
                2023,
                30000,
                10000,
                new CityResponseDto("Houston"),
                new StateResponseDto("Texas"),
                SellerType.DEALER,
                "Blue",
                "Automatic",
                "VIN12345",
                "V6",
                "AWD",
                FuelType.GASOLINE,
                20,
                30,
                "Leather",
                5,
                "Sedan",
                CarCondition.NEW,
                "None",
                "None",
                "3 years",
                List.of("Airbags", "Backup Camera"),
                "XLE",
                null,
                null,
                null,
                null,
                4,
                Status.AVAILABLE,
                "Inspected",
                null,
                null,
                null
        );
    }

    @Test
    void registerInventory_validInput_returnSavedInventory() {
        when(inventoryRepository.findInventoryByVINAndStatusNot(Mockito.anyString(),eq(Status.SOLD))).thenReturn(Optional.empty());
        when(inventoryMapper.inventoryRequestDtoToInventory(inventoryRequestDto)).thenReturn(inventory);
        when(makeRepository.findMakeByMakeName(Mockito.anyString())).thenReturn(Optional.of(make));
        when(modelRepository.getModelByModelName(Mockito.anyString())).thenReturn(Optional.of(model));
        when(cityRepository.findCityByCityName(Mockito.anyString())).thenReturn(Optional.of(city));
        when(stateRepository.findStateByStateName(Mockito.anyString())).thenReturn(Optional.of(state));
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);
        when(inventoryMapper.inventoryToInventoryResponseDto(inventory)).thenReturn(inventoryResponseDto);

        Optional<InventoryResponseDto> result = inventoryService.registerInventory(inventoryRequestDto, List.of("url1", "url2"));

        assertTrue(result.isPresent());
        assertEquals(inventoryResponseDto, result.get());

        verify(inventoryRepository, times(1)).save(inventory);
        verify(inventoryMapper, times(1)).inventoryRequestDtoToInventory(inventoryRequestDto);
    }

    @Test
    void updateInventory_validInput_returnUpdatedInventory() {
        when(inventoryRepository.findById(anyLong())).thenReturn(Optional.of(inventory));
        when(inventoryMapper.inventoryRequestDtoToInventory(inventoryRequestDto)).thenReturn(inventory);
        when(makeRepository.findMakeByMakeName(Mockito.anyString())).thenReturn(Optional.of(make));
        when(modelRepository.getModelByModelName(Mockito.anyString())).thenReturn(Optional.of(model));
        when(cityRepository.findCityByCityName(Mockito.anyString())).thenReturn(Optional.of(city));
        when(stateRepository.findStateByStateName(Mockito.anyString())).thenReturn(Optional.of(state));
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);
        when(inventoryMapper.inventoryToInventoryResponseDto(inventory)).thenReturn(inventoryResponseDto);

        Optional<InventoryResponseDto> result = inventoryService.updateInventory(1L, inventoryRequestDto, List.of("url1", "url2"));

        assertTrue(result.isPresent());
        assertEquals(inventoryResponseDto, result.get());

        verify(inventoryRepository, times(1)).findById(1L);
        verify(inventoryRepository, times(1)).save(inventory);
    }

    @Test
    void deleteInventory_returnNothing() {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));

        inventoryService.deleteInventory(1L);

        verify(inventoryRepository, times(1)).findById(1L);
        verify(inventoryRepository, times(1)).delete(inventory);
    }

    @Test
    void getAllInventories_returnInventories() {
        when(inventoryRepository.findInventoryByStatus(Status.AVAILABLE)).thenReturn(List.of(inventory));
        when(inventoryMapper.inventoryToInventoryResponseDto(inventory)).thenReturn(inventoryResponseDto);

        List<InventoryResponseDto> result = inventoryService.getAllInventories();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(inventoryResponseDto, result.get(0));

        verify(inventoryRepository, times(1)).findInventoryByStatus(Status.AVAILABLE);
    }

    @Test
    void getInventoryById_returnInventory() {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));
        when(inventoryMapper.inventoryToInventoryResponseDto(inventory)).thenReturn(inventoryResponseDto);

        Optional<InventoryResponseDto> result = inventoryService.getInventory(1L);

        assertTrue(result.isPresent());
        assertEquals(inventoryResponseDto, result.get());

        verify(inventoryRepository, times(1)).findById(1L);
    }

    @Test
    void getInventoryById_NotFound() {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> inventoryService.getInventory(1L));

        verify(inventoryRepository, times(1)).findById(1L);
    }

    @Test
    void getAllInventoriesWithFilterCar() {
        CarCriteriaRequest carCriteriaRequest = new CarCriteriaRequest("Toyota", "Camry", "Texas", "NEW", "Sedan",
                0, 50000, 10000.0, 30000.0, 2015, 2023, "GASOLINE");

        Inventory inventory2 = new Inventory();
        inventory2.setId(2L);
        inventory2.setCurrentPrice(15000);

        List<Inventory> inventories = List.of(inventory, inventory2);

        when(carDao.findAllByCriteria(carCriteriaRequest)).thenReturn(inventories);
        when(inventoryMapper.inventoryToInventoryResponseDto(any())).thenReturn(inventoryResponseDto);

        List<InventoryResponseDto> result = inventoryService.getAllInventoriesWithFilterCar("Toyota", "Camry", "Texas", "NEW", "Sedan",
                0, 50000, 10000.0, 30000.0, 2015, 2023, "GASOLINE");

        assertEquals(2, result.size());
        verify(carDao, times(1)).findAllByCriteria(any(CarCriteriaRequest.class));
    }

    @Test
    void getInventoriesByCarCondition_returnCarConditionInventories() {
        Inventory inventory2 = new Inventory();
        inventory2.setId(2L);
        inventory2.setCarCondition(CarCondition.NEW);

        List<Inventory> inventories = List.of(inventory, inventory2);
        when(inventoryRepository.findByCarCondition(any(CarCondition.class))).thenReturn(inventories);
        when(inventoryMapper.inventoryToInventoryResponseDto(any())).thenReturn(inventoryResponseDto);

        List<InventoryResponseDto> result=inventoryService.getInventoriesByCarCondition(CarCondition.NEW.name());
        assertEquals(2, result.size());
        verify(inventoryRepository, times(1)).findByCarCondition(any(CarCondition.class));
    }

    @Test
    void getInventory_returnInventory() {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));
        when(inventoryMapper.inventoryToInventoryResponseDto(any())).thenReturn(inventoryResponseDto);
        Optional<InventoryResponseDto> result = inventoryService.getInventory(1L);
        assertTrue(result.isPresent());
        assertEquals(inventoryResponseDto, result.get());
        verify(inventoryRepository, times(1)).findById(1L);

    }
}