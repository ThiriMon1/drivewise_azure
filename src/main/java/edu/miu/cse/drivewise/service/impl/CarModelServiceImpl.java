package edu.miu.cse.drivewise.service.impl;

import edu.miu.cse.drivewise.dto.request.CarModelRequestDto;
import edu.miu.cse.drivewise.dto.response.CarModelResponseDto;
import edu.miu.cse.drivewise.exception.make.MakeNotFoundException;
import edu.miu.cse.drivewise.mapper.ModelMapper;
import edu.miu.cse.drivewise.model.Make;
import edu.miu.cse.drivewise.model.CarModel;
import edu.miu.cse.drivewise.repository.MakeRepository;
import edu.miu.cse.drivewise.repository.ModelRepository;
import edu.miu.cse.drivewise.service.CarModelService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CarModelServiceImpl implements CarModelService {
    private final ModelRepository modelRepository;
    private final MakeRepository makeRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<CarModelResponseDto> saveModel(String makename, CarModelRequestDto modelRequestDto) {
        Make make = makeRepository.findMakeByMakeName(makename).orElseThrow(()->new MakeNotFoundException(makename+ " is not found"));
        if (make!=null) {
            CarModel model = modelMapper.modelRequestDtoToModel(modelRequestDto);
            model.setMake(make);
            System.out.println("Searching for make: " + model.getMake().getMakeName());
            modelRepository.save(model);
            return Optional.of(modelMapper.ModelToModelResponseDto(model));
        }
        throw new MakeNotFoundException(makename + " is not found");
    }

    @Override
    public List<CarModelResponseDto> getCarModelsByMakeName(String makename) {
        List<CarModel> carModels=modelRepository.findCarModelByMake_MakeName(makename);
        List<CarModelResponseDto> carModelResponseDtos=new ArrayList<>();
        if(carModels!=null){
            for(CarModel carModel:carModels){
                carModelResponseDtos.add(modelMapper.ModelToModelResponseDto(carModel));
            }
            return carModelResponseDtos;
        }
        else {
            throw new MakeNotFoundException(makename + " is not found");
        }
    }
}
