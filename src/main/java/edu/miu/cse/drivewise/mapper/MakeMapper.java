package edu.miu.cse.drivewise.mapper;

import edu.miu.cse.drivewise.dto.request.MakeRequestDto;
import edu.miu.cse.drivewise.dto.response.MakeResponseDto;
import edu.miu.cse.drivewise.model.Make;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MakeMapper {
//    @Mapping(source = "makeRequestDto.modelRequestDtoList", target = "modelList")
    Make makeRequestDtoToMake(MakeRequestDto makeRequestDto);
//
 //   @Mapping(source="make.modelList", target = "modelResponseDtoList")
    MakeResponseDto makeToMakeResponseDto(Make make);
}
