package edu.miu.cse.drivewise.service.impl;

import edu.miu.cse.drivewise.dto.request.MakeRequestDto;
import edu.miu.cse.drivewise.dto.response.MakeResponseDto;
import edu.miu.cse.drivewise.exception.make.MakeNotFoundException;
import edu.miu.cse.drivewise.mapper.MakeMapper;
import edu.miu.cse.drivewise.model.Make;
import edu.miu.cse.drivewise.repository.MakeRepository;
import edu.miu.cse.drivewise.service.MakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MakeServiceImpl implements MakeService {
    private final MakeRepository makeRepository;
    private final MakeMapper makeMapper;

    @Override
    public Optional<MakeResponseDto> saveMake(MakeRequestDto makeRequestDto) {
        // Map Dto to entity
        Make make = makeMapper.makeRequestDtoToMake(makeRequestDto);
        // Save
        return Optional.of(makeMapper.makeToMakeResponseDto(makeRepository.save(make)));
    }

    @Override
    public Optional<MakeResponseDto> findMakeByName(String makeName) {
        Optional<Make> foundMake = makeRepository.findMakeByMakeName(makeName);
        if(foundMake.isPresent()){
            return Optional.of(makeMapper.makeToMakeResponseDto(foundMake.get()));
        }
        throw new MakeNotFoundException(makeName +" is not found");
    }

    @Override
    public List<MakeResponseDto> findAllMake() {
        List<Make> makeList = makeRepository.findAll();
        List<MakeResponseDto> makeResponseDtoList = makeList.stream()
                .map(makeMapper::makeToMakeResponseDto)
                .collect(Collectors.toList());
        return makeResponseDtoList;
    }
}
