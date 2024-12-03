package edu.miu.cse.drivewise.controller;

import edu.miu.cse.drivewise.dto.request.CarModelRequestDto;
import edu.miu.cse.drivewise.dto.request.CarReviewRequestDto;
import edu.miu.cse.drivewise.dto.request.CustomerRequestDto;
import edu.miu.cse.drivewise.dto.request.MakeRequestDto;
import edu.miu.cse.drivewise.dto.response.CarModelResponseDto;
import edu.miu.cse.drivewise.dto.response.CarReviewResponseDto;
import edu.miu.cse.drivewise.dto.response.CustomerResponseDto;
import edu.miu.cse.drivewise.dto.response.MakeResponseDto;
import edu.miu.cse.drivewise.service.CarReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CarReviewControllerTest {
    @Mock
    private CarReviewService carReviewService;
    @InjectMocks
    private CarReviewController carReviewController;

    @Test
    void createReview_validInput_returnCreatedReview() {
        CarReviewRequestDto carReviewRequestDto=new CarReviewRequestDto("sdfdf",4,new MakeRequestDto("toyota"),new CarModelRequestDto("corolla"),2019,new CustomerRequestDto("123@gmail.com"));
        CarReviewResponseDto carReviewResponseDto=new CarReviewResponseDto(11L,"dfdf great", 4, new CustomerResponseDto(1L,"u1","smith","u1@gmail.com"),new MakeResponseDto("toyota")
        ,new CarModelResponseDto("corolla"), 2018, LocalDateTime.now());

        Mockito.when(carReviewService.createCarReview(Mockito.any(CarReviewRequestDto.class))).thenReturn(Optional.of(carReviewResponseDto));
        ResponseEntity<CarReviewResponseDto> response=carReviewController.createReview(carReviewRequestDto);

        assert response.getStatusCode()== HttpStatus.CREATED;


    }

    @Test
    void getAllReviews_returnAllReviews() {
        List<CarReviewResponseDto> carReviewResponseDtoList=new ArrayList<>();
        carReviewResponseDtoList.add(new CarReviewResponseDto(11L,"dfdf great", 4, new CustomerResponseDto(1L,"u1","smith","u1@gmail.com"),new MakeResponseDto("toyota")
                ,new CarModelResponseDto("corolla"), 2018, LocalDateTime.now()));

        Mockito.when(carReviewService.getAllCarReview()).thenReturn( carReviewResponseDtoList);

        ResponseEntity<List<CarReviewResponseDto>> response=carReviewController.getAllReviews();
        assert response.getStatusCode()== HttpStatus.OK;
    }

    @Test
    void getFilteredReviews_returnFilteredReviews() {
        List<CarReviewResponseDto> carReviewResponseDtoList=new ArrayList<>();
        carReviewResponseDtoList.add(new CarReviewResponseDto(11L,"dfdf great", 4, new CustomerResponseDto(1L,"u1","smith","u1@gmail.com"),new MakeResponseDto("toyota")
                ,new CarModelResponseDto("corolla"), 2018, LocalDateTime.now()));

        Mockito.when(carReviewService.getCarReviewByMakeModelYear("toyota","corolla",2019)).thenReturn( carReviewResponseDtoList);

        ResponseEntity<List<CarReviewResponseDto>> response=carReviewController.getFilteredReviews("toyota","corolla",2019);
        assert response.getStatusCode()== HttpStatus.OK;
    }

    @Test
    void updateReviewPartially_returnUpdatedReview() {
        CarReviewRequestDto carReviewRequestDto=new CarReviewRequestDto("sdfdf",4,new MakeRequestDto("toyota"),new CarModelRequestDto("corolla"),2019,new CustomerRequestDto("123@gmail.com"));
        CarReviewResponseDto carReviewResponseDto=new CarReviewResponseDto(11L,"dfdf great", 4, new CustomerResponseDto(1L,"u1","smith","u1@gmail.com"),new MakeResponseDto("toyota")
                ,new CarModelResponseDto("corolla"), 2018, LocalDateTime.now());

        Mockito.when(carReviewService.updateCarReviewPartially(Mockito.eq(11L),Mockito.eq(11L),Mockito.any(CarReviewRequestDto.class))).thenReturn(Optional.of(carReviewResponseDto));
        ResponseEntity<CarReviewResponseDto> response=carReviewController.updateReviewPartially(11L,11L,carReviewRequestDto);

        assert response.getStatusCode()== HttpStatus.OK;
    }

    @Test
    void updateReview_returnUpdatedReview() {
        CarReviewRequestDto carReviewRequestDto=new CarReviewRequestDto("sdfdf",4,new MakeRequestDto("toyota"),new CarModelRequestDto("corolla"),2019,new CustomerRequestDto("123@gmail.com"));
        CarReviewResponseDto carReviewResponseDto=new CarReviewResponseDto(11L,"dfdf great", 4, new CustomerResponseDto(1L,"u1","smith","u1@gmail.com"),new MakeResponseDto("toyota")
                ,new CarModelResponseDto("corolla"), 2018, LocalDateTime.now());

        Mockito.when(carReviewService.updateCarReview(Mockito.eq(11L),Mockito.eq(10L),Mockito.any(CarReviewRequestDto.class))).thenReturn(Optional.of(carReviewResponseDto));
        ResponseEntity<CarReviewResponseDto> response=carReviewController.updateReview(11L,10L,carReviewRequestDto);

        assert response.getStatusCode()== HttpStatus.OK;
    }

    @Test
    void deleteReview_returnNoContent() {
        Mockito.doNothing().when(carReviewService).deleteCarReview(1L,Mockito.anyLong());

        ResponseEntity<Void> response=carReviewController.deleteReview(1L,11L);
        assert response.getStatusCode()== HttpStatus.NO_CONTENT;
    }
}