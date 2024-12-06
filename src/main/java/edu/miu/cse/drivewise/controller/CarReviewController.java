package edu.miu.cse.drivewise.controller;

import edu.miu.cse.drivewise.dto.request.CarReviewRequestDto;
import edu.miu.cse.drivewise.dto.response.CarReviewResponseDto;
import edu.miu.cse.drivewise.service.CarReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class CarReviewController {
    private final CarReviewService carReviewService;

    // Add a car review
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PostMapping
    public ResponseEntity<CarReviewResponseDto> createReview(@RequestBody CarReviewRequestDto carReviewRequestDto) {
        Optional<CarReviewResponseDto> carReviewResponseDto = carReviewService.createCarReview(carReviewRequestDto);
        if (carReviewResponseDto.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(carReviewResponseDto.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    // Get all reviews
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<CarReviewResponseDto>> getAllReviews() {
        List<CarReviewResponseDto> carReviewResponseDtos = carReviewService.getAllCarReview();
        return ResponseEntity.status(HttpStatus.OK).body(carReviewResponseDtos);
    }

    // Get reviews by user
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @GetMapping("/user-reviews")
    public ResponseEntity<List<CarReviewResponseDto>> getAllReviewsByUser(
            @RequestParam Long userId
    ) {
        List<CarReviewResponseDto> carReviewResponseDtos = carReviewService.getCarReviewByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(carReviewResponseDtos);
    }

    // Get reviews with filter
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/filter-reviews")
    public ResponseEntity<List<CarReviewResponseDto>> getFilteredReviews(
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String carModel,
            @RequestParam(required = false) Integer year
    ) {
        List<CarReviewResponseDto> carReviewResponseDtos = new ArrayList<>();
        if (make != null && carModel != null && year != null) {

            carReviewResponseDtos=carReviewService.getCarReviewByMakeModelYear(make, carModel, year);
        }else if (make != null && carModel != null) {

            carReviewResponseDtos=carReviewService.getCarReviewByMakeAndModel(make, carModel);
        }else if(make!=null){

            carReviewResponseDtos=carReviewService.getCarReviewByMake(make);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(carReviewResponseDtos);

    }

    // Update an existing review partially
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PatchMapping("/{reviewid}")
    public ResponseEntity<CarReviewResponseDto> updateReviewPartially(
            @RequestParam Long userId,
            @PathVariable Long reviewid,
            @RequestBody CarReviewRequestDto carReviewRequestDto) {
        Optional<CarReviewResponseDto> carReviewResponseDto=carReviewService.updateCarReviewPartially(userId,reviewid, carReviewRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(carReviewResponseDto.get());
    }

    // Update an existing review
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PutMapping("/{reviewid}")
    public ResponseEntity<CarReviewResponseDto> updateReview(
            @RequestParam Long userId,
            @PathVariable Long reviewid,
            @RequestBody CarReviewRequestDto carReviewRequestDto) {
        Optional<CarReviewResponseDto> carReviewResponseDto=carReviewService.updateCarReview(userId,reviewid, carReviewRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(carReviewResponseDto.get());
    }

    // Delete a existing review
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @DeleteMapping("/{reviewid}")
    public ResponseEntity<Void> deleteReview(
            @RequestParam Long userId,
            @PathVariable Long reviewid) {
        carReviewService.deleteCarReview(userId,reviewid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
