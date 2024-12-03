package edu.miu.cse.drivewise.repository;

import edu.miu.cse.drivewise.dto.request.CarReviewRequestDto;
import edu.miu.cse.drivewise.dto.response.CarReviewResponseDto;
import edu.miu.cse.drivewise.model.CarModel;
import edu.miu.cse.drivewise.model.CarReview;
import edu.miu.cse.drivewise.model.Make;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarReviewRepository extends JpaRepository<CarReview,Long> {
    List<CarReview> findByMake(Make make);
    List<CarReview> findByMakeAndCarModel(Make make, CarModel model);
    List<CarReview> findByMakeAndCarModelAndYear(Make make,CarModel model, int year);
    List<CarReview> findCarReviewsByCustomer_UserId(Long userId);
    Optional<CarReview> findByReviewId(Long reviewId);
    Optional<CarReview> findCarReviewsByCustomer_UserIdAndReviewId(Long userId, Long reviewId);
}
