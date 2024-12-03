package edu.miu.cse.drivewise.mapper;

import edu.miu.cse.drivewise.dto.request.CustomerRequestDto;
import edu.miu.cse.drivewise.dto.response.CustomerResponseDto;
import edu.miu.cse.drivewise.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer CustomerRequestDtoToCustomer(CustomerRequestDto customerRequestDto);
    CustomerResponseDto CustomerToCustomerResponseDto(Customer customer);
}
