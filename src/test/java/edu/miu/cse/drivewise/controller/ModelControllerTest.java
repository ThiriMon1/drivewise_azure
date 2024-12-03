package edu.miu.cse.drivewise.controller;

import edu.miu.cse.drivewise.dto.request.CarModelRequestDto;
import edu.miu.cse.drivewise.dto.response.CarModelResponseDto;
import edu.miu.cse.drivewise.model.CarModel;
import edu.miu.cse.drivewise.service.CarModelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ModelControllerTest {
    @Mock
    CarModelService carModelService;
    @InjectMocks
    ModelController modelController;

    @Test
    void createModel_validInput_returnsModel() {
        // Mock the response from the service
        CarModelResponseDto mockResponse=new CarModelResponseDto("Corolla");
        Mockito.when(carModelService.saveModel(Mockito.anyString(),Mockito.any(CarModelRequestDto.class))).thenReturn(Optional.of(mockResponse));

        // Call controller method
        ResponseEntity<CarModelResponseDto> response=modelController.createModel("Toyota",new CarModelRequestDto("Corolla"));

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void getModelsByMakeName_returnsModels() {
        // Mock the response from the service
        CarModelResponseDto mockResponse=new CarModelResponseDto("Corolla");
        Mockito.when(carModelService.getCarModelsByMakeName(Mockito.anyString())).thenReturn(List.of(mockResponse));

        // Call controller method
        ResponseEntity<List<CarModelResponseDto>> response=modelController.getModelsByMakeName("Toyota");

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}