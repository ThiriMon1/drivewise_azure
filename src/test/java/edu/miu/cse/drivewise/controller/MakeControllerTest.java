package edu.miu.cse.drivewise.controller;

import edu.miu.cse.drivewise.dto.request.MakeRequestDto;
import edu.miu.cse.drivewise.dto.response.MakeResponseDto;
import edu.miu.cse.drivewise.service.MakeService;
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
class MakeControllerTest {
    @Mock
    MakeService makeService;
    @InjectMocks
    MakeController makeController;

    @Test
    void createMake_validInput_returnMake() {
        // Mock the response from the service
        MakeResponseDto mockResponse=new MakeResponseDto("Toyota");
        Mockito.when(makeService.saveMake(Mockito.any(MakeRequestDto.class))).thenReturn(Optional.of(mockResponse));

        // Call controller method
        ResponseEntity<MakeResponseDto> response=makeController.createMake(new MakeRequestDto("Toyota"));

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void getAllMakes() {
        // Mock the response from the service
        MakeResponseDto mockResponse=new MakeResponseDto("Toyota");
        List<MakeResponseDto> mockResponses=List.of(mockResponse);
        Mockito.when(makeService.findAllMake()).thenReturn(mockResponses);

        // Call controller method
        ResponseEntity<List<MakeResponseDto>> response=makeController.getAllMakes();

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getMakeByName() {
        // Mock the response from the service
        MakeResponseDto mockResponse=new MakeResponseDto("Toyota");
        Mockito.when(makeService.findMakeByName("Toyota")).thenReturn(Optional.of(mockResponse));

        ResponseEntity<MakeResponseDto> response=makeController.getMakeByName("Toyota");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}