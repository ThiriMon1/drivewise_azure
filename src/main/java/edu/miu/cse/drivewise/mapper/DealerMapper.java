package edu.miu.cse.drivewise.mapper;

import edu.miu.cse.drivewise.dto.request.DealerRequestDto;
import edu.miu.cse.drivewise.dto.response.DealerResponseDto;
import edu.miu.cse.drivewise.model.Dealer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DealerMapper {

    Dealer dealerRequestDtoToDealer(DealerRequestDto dealerRequestDto);

    DealerResponseDto dealerToDealerResponseDto(Dealer dealer);
}
