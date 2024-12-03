package edu.miu.cse.drivewise.service;

import edu.miu.cse.drivewise.dto.request.CarModelRequestDto;
import edu.miu.cse.drivewise.dto.response.CarModelResponseDto;

import java.util.List;
import java.util.Optional;

public interface CarModelService {
    Optional<CarModelResponseDto> saveModel(String makename, CarModelRequestDto modelRequestDto);
    List<CarModelResponseDto> getCarModelsByMakeName(String makename);
}
