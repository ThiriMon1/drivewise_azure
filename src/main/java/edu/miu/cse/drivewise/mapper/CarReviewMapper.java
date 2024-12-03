package edu.miu.cse.drivewise.mapper;

import edu.miu.cse.drivewise.dto.request.CarReviewRequestDto;
import edu.miu.cse.drivewise.dto.response.CarReviewResponseDto;
import edu.miu.cse.drivewise.model.CarReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarReviewMapper {
    CarReview CarReviewRequestDtoToCarReview(CarReviewRequestDto carReviewRequestDto);

    @Mapping(source = "carReview.customer", target = "customer")
    CarReviewResponseDto carReviewToCarReviewResponseDto(CarReview carReview);
}
