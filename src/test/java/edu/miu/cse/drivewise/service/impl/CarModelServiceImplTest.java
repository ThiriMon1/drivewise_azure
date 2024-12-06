package edu.miu.cse.drivewise.service.impl;

import edu.miu.cse.drivewise.dto.request.CarModelRequestDto;
import edu.miu.cse.drivewise.dto.response.CarModelResponseDto;
import edu.miu.cse.drivewise.exception.make.MakeNotFoundException;
import edu.miu.cse.drivewise.mapper.ModelMapper;
import edu.miu.cse.drivewise.model.Make;
import edu.miu.cse.drivewise.model.CarModel;
import edu.miu.cse.drivewise.repository.MakeRepository;
import edu.miu.cse.drivewise.repository.ModelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarModelServiceImplTest {

    @Mock
    private ModelRepository modelRepository;

    @Mock
    private MakeRepository makeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CarModelServiceImpl carModelService;

    @Test
    void saveModel_ShouldSaveAndReturnModel_WhenMakeExists() {
        // Arrange
        String makeName = "Toyota";
        CarModelRequestDto requestDto = new CarModelRequestDto("Corolla");
        Make make=new Make();
        make.setMakeName(makeName);
        CarModel model = new CarModel();
        model.setModelName(requestDto.modelName());
        model.setMake(make);
        CarModelResponseDto responseDto = new CarModelResponseDto(requestDto.modelName());

        Mockito.when(makeRepository.findMakeByMakeName(Mockito.anyString())).thenReturn(Optional.of(make));
        Mockito.when(modelMapper.modelRequestDtoToModel(Mockito.any(CarModelRequestDto.class))).thenReturn(model);
        Mockito.when(modelRepository.save(Mockito.any(CarModel.class))).thenReturn(model);
        Mockito.when(modelMapper.ModelToModelResponseDto(Mockito.any(CarModel.class))).thenReturn(responseDto);

        // Act
        Optional<CarModelResponseDto> result = carModelService.saveModel(makeName, requestDto);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(requestDto.modelName(), result.get().modelName());
        verify(makeRepository, times(1)).findMakeByMakeName(makeName);
        verify(modelRepository, times(1)).save(model);
    }

    @Test
    void saveModel_ShouldThrowException_WhenMakeDoesNotExist() {
        // Arrange
        String makeName = "UnknownMake";
        CarModelRequestDto requestDto = new CarModelRequestDto("ModelX");

        when(makeRepository.findMakeByMakeName(Mockito.anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(MakeNotFoundException.class, () -> carModelService.saveModel(makeName, requestDto));
        verify(makeRepository, times(1)).findMakeByMakeName(makeName);
        verify(modelRepository, never()).save(any());
    }

    @Test
    void getCarModelsByMakeName_ShouldReturnModels_WhenMakeExists() {
        // Arrange
        Make make = new Make();
        String makeName = "Toyota";
        make.setMakeName(makeName);
        CarModel carModel1 = new CarModel();
        carModel1.setModelName("Corolla");
        List<CarModel> carModels = List.of(carModel1);
        CarModelResponseDto responseDto1 = new CarModelResponseDto("Corolla");

        Mockito.when(makeRepository.findMakeByMakeName(Mockito.anyString())).thenReturn(Optional.of(make));
        Mockito.when(modelRepository.findCarModelByMake_MakeName(Mockito.anyString())).thenReturn(carModels);
        Mockito.when(modelMapper.ModelToModelResponseDto(Mockito.any(CarModel.class))).thenReturn(responseDto1);

        // Act
        List<CarModelResponseDto> result = carModelService.getCarModelsByMakeName(makeName);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Corolla", result.get(0).modelName());
        verify(modelRepository, times(1)).findCarModelByMake_MakeName(makeName);
    }

}
