package edu.miu.cse.drivewise.service;

import edu.miu.cse.drivewise.dto.request.MakeRequestDto;
import edu.miu.cse.drivewise.dto.response.MakeResponseDto;

import java.util.List;
import java.util.Optional;

public interface MakeService {
    Optional<MakeResponseDto> saveMake(MakeRequestDto makeRequestDto);
    Optional<MakeResponseDto> findMakeByName(String makeName);
    List<MakeResponseDto> findAllMake();
}
