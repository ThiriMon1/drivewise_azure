package edu.miu.cse.drivewise.service;

import edu.miu.cse.drivewise.dto.request.CarReviewRequestDto;
import edu.miu.cse.drivewise.dto.response.CarReviewResponseDto;

import java.util.List;
import java.util.Optional;

public interface CarReviewService {
    Optional<CarReviewResponseDto> createCarReview(CarReviewRequestDto carReviewRequestDto);
    List<CarReviewResponseDto> getAllCarReview();
    List<CarReviewResponseDto> getCarReviewByUser(Long userId);
    List<CarReviewResponseDto> getCarReviewByMake(String make);
    List<CarReviewResponseDto> getCarReviewByMakeAndModel(String make,String model);
    List<CarReviewResponseDto> getCarReviewByMakeModelYear(String make,String model, int year);
    Optional<CarReviewResponseDto> updateCarReview(Long userId,Long reviewId, CarReviewRequestDto carReviewRequestDto);
    Optional<CarReviewResponseDto> updateCarReviewPartially(Long userId,Long reviewId, CarReviewRequestDto carReviewRequestDto);
    void deleteCarReview(Long userId,Long reviewId);
}
