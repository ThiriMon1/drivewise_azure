package edu.miu.cse.drivewise.controller;

import edu.miu.cse.drivewise.client.ImageStorageClient;
import edu.miu.cse.drivewise.dto.request.CarModelRequestDto;
import edu.miu.cse.drivewise.dto.request.InventoryRequestDto;
import edu.miu.cse.drivewise.dto.request.MakeRequestDto;
import edu.miu.cse.drivewise.dto.response.*;
import edu.miu.cse.drivewise.model.*;
import edu.miu.cse.drivewise.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {
    @Mock
    InventoryService inventoryService;
    @Mock
    ImageStorageClient imageStorageClient;
    @InjectMocks
    InventoryController inventoryController;

    private InventoryResponseDto inventoryResponseDto;
    private InventoryRequestDto inventoryRequestDto;
    private List<MockMultipartFile> photos;

    @BeforeEach
    void setUp() {
        // Initialize shared mock data
        inventoryResponseDto = new InventoryResponseDto(
                1L, new MakeResponseDto("Toyota"), new CarModelResponseDto("Corolla"), 2019, 19000, 50000,
                new CityResponseDto("Chicago"), new StateResponseDto("Illinois"), SellerType.PRIVATE, "black",
                "Automatic", "2HGFC2F59KH123457", "2.0L I4", "FWD", FuelType.GASOLINE, 20, 27, "Leather", 7, "Sedan",
                CarCondition.USED, "2", "Minor accident reported", "3-month limited warranty",
                List.of("Sunroof", "Bluetooth", "Backup Camera", "Heated Seats"), "Sport", null, null,
                LocalDate.now(), null, 4.7, Status.AVAILABLE, "Passed 100-point inspection", LocalDate.now(),
                LocalDateTime.now(), LocalDateTime.now()
        );

        inventoryRequestDto = new InventoryRequestDto(
                1L, new MakeRequestDto("Toyota"), new CarModelRequestDto("Corolla"), 2019, 19000, 50000,
                null, null, SellerType.PRIVATE, "black", "Automatic", "2HGFC2F59KH123457", "2.0L I4", "FWD",
                FuelType.GASOLINE, 20, 27, "Leather", 7, "Sedan", CarCondition.USED, "2", "Minor accident reported",
                "3-month limited warranty", List.of("Sunroof", "Bluetooth", "Backup Camera", "Heated Seats"),
                "Sport", null, null, LocalDate.now(), null, 4.7, Status.AVAILABLE, "Passed 100-point inspection",
                LocalDate.now(), LocalDateTime.now(), LocalDateTime.now()
        );

        photos = List.of(
                new MockMultipartFile("photos", "picture1.png", "image/png", new byte[]{1, 2, 3}),
                new MockMultipartFile("photos", "picture2.png", "image/png", new byte[]{4, 5, 6})
        );

    }

    @Test
    void createInventory_validInput_returnInventory() throws IOException {

        MockMultipartFile photo1 = new MockMultipartFile("photos", "picture1.png", "image/png", new byte[]{1, 2, 3});
        MockMultipartFile photo2 = new MockMultipartFile("photos", "picture2.png", "image/png", new byte[]{4, 5, 6});

        //Mocking behavior
        when(imageStorageClient.uploadImage(Mockito.anyString(), any(InputStream.class), Mockito.anyLong()))
                .thenReturn("https://mockstorage.com/picture1.png", "https://mockstorage.com/picture2.png");
        when(inventoryService.registerInventory(any(InventoryRequestDto.class), anyList()))
                .thenReturn(Optional.of(inventoryResponseDto));

        // Call controller method
        ResponseEntity<InventoryResponseDto> response = inventoryController.createInventory(inventoryRequestDto, List.of(photo1, photo2));

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void updateInventory_validInput_returnInventory() throws IOException {
        MockMultipartFile photo1 = new MockMultipartFile("photos", "picture1.png", "image/png", new byte[]{1, 2, 3});
        MockMultipartFile photo2 = new MockMultipartFile("photos", "picture2.png", "image/png", new byte[]{4, 5, 6});

        //Mocking behavior
        when(imageStorageClient.uploadImage(Mockito.anyString(), any(InputStream.class), Mockito.anyLong()))
                .thenReturn("https://mockstorage.com/picture1.png", "https://mockstorage.com/picture2.png");
        when(inventoryService.updateInventory(Mockito.anyLong(), any(InventoryRequestDto.class), anyList()))
                .thenReturn(Optional.of(inventoryResponseDto));

        // Call controller method
        ResponseEntity<InventoryResponseDto> response = inventoryController.updateInventory(1L,inventoryRequestDto, List.of(photo1, photo2));

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void getAllInventories_return() {
        // Mock the response from the service
        List<InventoryResponseDto> mockResponse = List.of(inventoryResponseDto);

        Mockito.when(inventoryService.getAllInventories()).thenReturn(mockResponse);
        ResponseEntity<List<InventoryResponseDto>> response = inventoryController.getAllInventories();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAllInventoriesWithFilterCar_return() {
        // Mock the response from the service
        List<InventoryResponseDto> mockResponse = List.of(inventoryResponseDto);

        Mockito.when(inventoryService.getAllInventoriesWithFilterCar(
                        Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                        Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyDouble(),
                        Mockito.anyDouble(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString()))
                .thenReturn(mockResponse);

        // Call the controller method
        ResponseEntity<List<InventoryResponseDto>> response = inventoryController.getAllInventoriesWithFilterCar(
                "Toyota", "Corolla", "Illinois", "USED", "Sedan", 20000, 60000, 15000.0, 25000.0,
                2015, 2020, "GASOLINE");

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Toyota", response.getBody().get(0).make().makeName());
        assertEquals("Corolla", response.getBody().get(0).model().modelName());

        // Verify the mocked service method was called with the expected parameters
        Mockito.verify(inventoryService).getAllInventoriesWithFilterCar(
                "Toyota", "Corolla", "Illinois", "USED", "Sedan", 20000, 60000, 15000.0, 25000.0,
                2015, 2020, "GASOLINE");
    }

    @Test
    void getInventoriesByCarCondition_return() {
        // Mock the response from the service
        List<InventoryResponseDto> mockResponse = List.of(inventoryResponseDto);
        Mockito.when(inventoryService.getInventoriesByCarCondition(Mockito.anyString())).thenReturn(mockResponse);

        // Call the controller method
        ResponseEntity<List<InventoryResponseDto>> response = inventoryController.getInventoriesByCarCondition("NEW");

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Toyota", response.getBody().get(0).make().makeName());
        assertEquals("Corolla", response.getBody().get(0).model().modelName());
    }

    @Test
    void deleteInventory() {

        Mockito.doNothing().when(inventoryService).deleteInventory(Mockito.anyLong());

        ResponseEntity<Void> response = inventoryController.deleteInventory(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}