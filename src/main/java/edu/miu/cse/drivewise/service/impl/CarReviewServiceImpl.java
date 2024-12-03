package edu.miu.cse.drivewise.service.impl;

import edu.miu.cse.drivewise.dto.request.CarReviewRequestDto;
import edu.miu.cse.drivewise.dto.response.CarReviewResponseDto;
import edu.miu.cse.drivewise.exception.carReview.CarReviewNotFoundException;
import edu.miu.cse.drivewise.exception.carmodel.CarModelNotFoundException;
import edu.miu.cse.drivewise.exception.make.MakeNotFoundException;
import edu.miu.cse.drivewise.exception.customer.CustomerNotFoundException;
import edu.miu.cse.drivewise.mapper.CarReviewMapper;
import edu.miu.cse.drivewise.model.CarModel;
import edu.miu.cse.drivewise.model.CarReview;
import edu.miu.cse.drivewise.model.Customer;
import edu.miu.cse.drivewise.model.Make;
import edu.miu.cse.drivewise.repository.CarReviewRepository;
import edu.miu.cse.drivewise.repository.CustomerRepository;
import edu.miu.cse.drivewise.repository.MakeRepository;
import edu.miu.cse.drivewise.repository.ModelRepository;
import edu.miu.cse.drivewise.service.CarReviewService;
import edu.miu.cse.drivewise.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarReviewServiceImpl implements CarReviewService {
    private final CarReviewRepository carReviewRepository;
    private final CarReviewMapper carReviewMapper;
    private final MakeRepository makeRepository;
    private final ModelRepository modelRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Optional<CarReviewResponseDto> createCarReview(CarReviewRequestDto carReviewRequestDto) {
        CarReview carReview=carReviewMapper.CarReviewRequestDtoToCarReview(carReviewRequestDto);

        carReview.setCarModel(modelRepository.getModelByModelName(carReview.getCarModel().getModelName())
                .orElseThrow(()->new CarModelNotFoundException(carReview.getCarModel().getModelName()+"is not found!")));

        carReview.setMake(makeRepository.findMakeByMakeName(carReview.getMake().getMakeName())
                .orElseThrow(()->new MakeNotFoundException(carReview.getMake().getMakeName()+" is not found!")));

        carReview.setCustomer(customerRepository.findCustomerByEmail(carReview.getCustomer().getEmail())
                .orElseThrow(()->new CustomerNotFoundException(carReview.getCustomer().getEmail()+" is not found!")));

        CarReview saveReview=carReviewRepository.save(carReview);
        return Optional.of(carReviewMapper.carReviewToCarReviewResponseDto(saveReview));
    }

    @Override
    public List<CarReviewResponseDto> getAllCarReview() {
        List<CarReview> carReviews=carReviewRepository.findAll();
        List<CarReviewResponseDto> carReviewResponseDtos=new ArrayList<>();
        for(CarReview carReview:carReviews){
            carReviewResponseDtos.add(carReviewMapper.carReviewToCarReviewResponseDto(carReview));
        }
        return carReviewResponseDtos;
    }

    @Override
    public List<CarReviewResponseDto> getCarReviewByUser(Long userId) {
        List<CarReview> carReviews=carReviewRepository.findCarReviewsByCustomer_UserId(userId);
        List<CarReviewResponseDto> carReviewResponseDtos=new ArrayList<>();
        for(CarReview carReview:carReviews){
            carReviewResponseDtos.add(carReviewMapper.carReviewToCarReviewResponseDto(carReview));
        }
        return carReviewResponseDtos;
    }

    @Override
    public List<CarReviewResponseDto> getCarReviewByMake(String make) {
        List<CarReview> carReviews=new ArrayList<>();
        Optional<Make> foundMake = makeRepository.findMakeByMakeName(make);
        if(foundMake.isPresent()){
            carReviews=carReviewRepository.findByMake(foundMake.get());
        }
       List<CarReviewResponseDto> carReviewResponseDtos=new ArrayList<>();
        for(CarReview carReview:carReviews){
            carReviewResponseDtos.add(carReviewMapper.carReviewToCarReviewResponseDto(carReview));
        }
        return carReviewResponseDtos;
    }

    @Override
    public List<CarReviewResponseDto> getCarReviewByMakeAndModel(String make, String model) {
        List<CarReview> carReviews=new ArrayList<>();
        Optional<Make> foundMake = makeRepository.findMakeByMakeName(make);
        Optional<CarModel> foundModel=modelRepository.getModelByModelName(model);
        if(foundMake.isPresent() && foundModel.isPresent()){
            carReviews=carReviewRepository.findByMakeAndCarModel(foundMake.get(),foundModel.get());
        }
        List<CarReviewResponseDto> carReviewResponseDtos=new ArrayList<>();
        for(CarReview carReview:carReviews){
            carReviewResponseDtos.add(carReviewMapper.carReviewToCarReviewResponseDto(carReview));
        }
        return carReviewResponseDtos;
    }

    @Override
    public List<CarReviewResponseDto> getCarReviewByMakeModelYear(String make, String model, int year) {
        List<CarReview> carReviews=new ArrayList<>();
        Optional<Make> foundMake = makeRepository.findMakeByMakeName(make);
        Optional<CarModel> foundModel=modelRepository.getModelByModelName(model);
        if(foundMake.isPresent() && foundModel.isPresent()){
            carReviews=carReviewRepository.findByMakeAndCarModelAndYear(foundMake.get(),foundModel.get(),year);
        }
        List<CarReviewResponseDto> carReviewResponseDtos=new ArrayList<>();
        for(CarReview carReview:carReviews){
            carReviewResponseDtos.add(carReviewMapper.carReviewToCarReviewResponseDto(carReview));
        }
        return carReviewResponseDtos;
    }


    @Override
    public Optional<CarReviewResponseDto> updateCarReview(Long userId,Long reviewId, CarReviewRequestDto carReviewRequestDto) {
        Optional<CarReview> foundCarReview=carReviewRepository.findCarReviewsByCustomer_UserIdAndReviewId(userId,reviewId);
        if(foundCarReview.isPresent()){
            CarReview carReview=foundCarReview.get();
            carReview.setCarModel(modelRepository.getModelByModelName(carReviewRequestDto.carModel().modelName())
                    .orElseThrow(()->new CarModelNotFoundException(carReviewRequestDto.carModel().modelName()+"is not found!")));

            carReview.setMake(makeRepository.findMakeByMakeName(carReviewRequestDto.make().makeName())
                    .orElseThrow(()->new MakeNotFoundException(carReviewRequestDto.make().makeName()+" is not found!")));

            carReview.setCustomer(customerRepository.findCustomerByEmail(carReviewRequestDto.customer().email())
                    .orElseThrow(()->new CustomerNotFoundException(carReviewRequestDto.customer().email()+" is not found!")));
            carReview.setContent(carReviewRequestDto.content());
            carReview.setStar(carReviewRequestDto.star());
            carReview.setYear(carReviewRequestDto.year());

            CarReview savedCarReview=carReviewRepository.save(foundCarReview.get());
            return Optional.of(carReviewMapper.carReviewToCarReviewResponseDto(savedCarReview));
        }else {
            throw new CarReviewNotFoundException(reviewId + " is not found!");
        }
    }

    @Override
    public Optional<CarReviewResponseDto> updateCarReviewPartially(Long userId,Long reviewId, CarReviewRequestDto carReviewRequestDto) {
        Optional<CarReview> foundCarReview=carReviewRepository.findCarReviewsByCustomer_UserIdAndReviewId(userId,reviewId);
        if(foundCarReview.isPresent()){
            if(carReviewRequestDto.carModel()!=null){
                foundCarReview.get().setCarModel(modelRepository.getModelByModelName(carReviewRequestDto.carModel().modelName())
                        .orElseThrow(()->new CarModelNotFoundException(carReviewRequestDto.carModel().modelName()+" is not found!")));
            }
            if(carReviewRequestDto.make()!=null){
                foundCarReview.get().setMake(makeRepository.findMakeByMakeName(carReviewRequestDto.make().makeName())
                        .orElseThrow(()->new MakeNotFoundException(carReviewRequestDto.make().makeName()+" is not found!")));
            }
            if(carReviewRequestDto.customer()!=null){
                foundCarReview.get().setCustomer(customerRepository.findCustomerByEmail(carReviewRequestDto.customer().email())
                        .orElseThrow(()->new CustomerNotFoundException(carReviewRequestDto.customer().email()+" is not found!")));
            }
            if(carReviewRequestDto.content()!=null){
                foundCarReview.get().setContent(carReviewRequestDto.content());
            }
            if(carReviewRequestDto.star()!=null){
                foundCarReview.get().setStar(carReviewRequestDto.star());
            }
            if(carReviewRequestDto.year()!=null){
                foundCarReview.get().setYear(carReviewRequestDto.year());
            }
            CarReview savedCarReview=carReviewRepository.save(foundCarReview.get());
            return Optional.of(carReviewMapper.carReviewToCarReviewResponseDto(savedCarReview));
        }else {
            throw new CarReviewNotFoundException(reviewId + " is not found!");
        }
    }

    @Override
    public void deleteCarReview(Long userId,Long reviewId) {
        carReviewRepository.findCarReviewsByCustomer_UserIdAndReviewId(userId,reviewId)
                .orElseThrow(()-> new CarReviewNotFoundException(reviewId+" is not found"));
            carReviewRepository.deleteById(reviewId);
        }

}
