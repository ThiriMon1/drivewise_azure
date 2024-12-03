package edu.miu.cse.drivewise.mapper;

import edu.miu.cse.drivewise.dto.request.CarModelRequestDto;
import edu.miu.cse.drivewise.dto.response.CarModelResponseDto;
import edu.miu.cse.drivewise.model.CarModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ModelMapper {
  //  @Mapping(source = "modelRequestDto.makeRequestDto", target = "make")
    CarModel modelRequestDtoToModel(CarModelRequestDto modelRequestDto);

//    @Mapping(source = "carModel.make", target = "makeResponseDto")
    CarModelResponseDto ModelToModelResponseDto(CarModel carModel);
}
