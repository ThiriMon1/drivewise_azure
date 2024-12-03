package edu.miu.cse.drivewise.service.impl;

import edu.miu.cse.drivewise.dto.request.CarReviewRequestDto;
import edu.miu.cse.drivewise.dto.request.CarModelRequestDto;
import edu.miu.cse.drivewise.dto.request.CustomerRequestDto;
import edu.miu.cse.drivewise.dto.request.MakeRequestDto;
import edu.miu.cse.drivewise.dto.response.CarReviewResponseDto;
import edu.miu.cse.drivewise.dto.response.CustomerResponseDto;
import edu.miu.cse.drivewise.mapper.CarReviewMapper;
import edu.miu.cse.drivewise.model.CarModel;
import edu.miu.cse.drivewise.model.CarReview;
import edu.miu.cse.drivewise.model.Customer;
import edu.miu.cse.drivewise.model.Make;
import edu.miu.cse.drivewise.repository.CarReviewRepository;
import edu.miu.cse.drivewise.repository.CustomerRepository;
import edu.miu.cse.drivewise.repository.MakeRepository;
import edu.miu.cse.drivewise.repository.ModelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarReviewServiceImplTest {

    @Mock
    private CarReviewRepository carReviewRepository;

    @Mock
    private CarReviewMapper carReviewMapper;

    @Mock
    private MakeRepository makeRepository;

    @Mock
    private ModelRepository modelRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CarReviewServiceImpl carReviewService;

    @Test
    void createCarReview_ShouldReturnSavedReview() {
        // Arrange
        CarModelRequestDto carModelRequestDto = new CarModelRequestDto("Camry");
        MakeRequestDto makeRequestDto = new MakeRequestDto("Toyota");
        CustomerRequestDto customerRequestDto = new CustomerRequestDto("customer@example.com");

        CarReviewRequestDto requestDto = new CarReviewRequestDto(
                "Great car!", 5, makeRequestDto, carModelRequestDto, 2023, customerRequestDto
        );

        CarModel carModel = new CarModel();
        carModel.setModelName("Camry");

        Make make = new Make();
        make.setMakeName("Toyota");

        Customer customer = new Customer();
        customer.setEmail("customer@example.com");

        CarReview carReview = new CarReview();
        carReview.setContent("Great car!");
        carReview.setStar(5);
        carReview.setMake(make);
        carReview.setCarModel(carModel);
        carReview.setCustomer(customer);

        CarReviewResponseDto responseDto = new CarReviewResponseDto(1L, "Great car!", 5, null, null, null, 2023, null);

        when(modelRepository.getModelByModelName(Mockito.anyString())).thenReturn(Optional.of(carModel));
        when(makeRepository.findMakeByMakeName(Mockito.anyString())).thenReturn(Optional.of(make));
        when(customerRepository.findCustomerByEmail(Mockito.anyString())).thenReturn(Optional.of(customer));
        when(carReviewMapper.CarReviewRequestDtoToCarReview(requestDto)).thenReturn(carReview);
        when(carReviewRepository.save(carReview)).thenReturn(carReview);
        when(carReviewMapper.carReviewToCarReviewResponseDto(carReview)).thenReturn(responseDto);

        // Act
        Optional<CarReviewResponseDto> result = carReviewService.createCarReview(requestDto);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Great car!", result.get().content());
        assertEquals(5, result.get().star());
        verify(carReviewRepository, times(1)).save(carReview);
    }

    @Test
    void getAllCarReview_ShouldReturnAllReviews() {
        // Arrange
        CarReview carReview1 = new CarReview();
        carReview1.setContent("Review 1");
        CarReview carReview2 = new CarReview();
        carReview2.setContent("Review 2");

        List<CarReview> carReviews = List.of(carReview1, carReview2);
        CarReviewResponseDto responseDto1 = new CarReviewResponseDto(1L, "Review 1", 5, null, null, null, 2023, null);
        CarReviewResponseDto responseDto2 = new CarReviewResponseDto(2L, "Review 2", 4, null, null, null, 2023, null);

        when(carReviewRepository.findAll()).thenReturn(carReviews);
        when(carReviewMapper.carReviewToCarReviewResponseDto(carReview1)).thenReturn(responseDto1);
        when(carReviewMapper.carReviewToCarReviewResponseDto(carReview2)).thenReturn(responseDto2);

        // Act
        List<CarReviewResponseDto> result = carReviewService.getAllCarReview();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Review 1", result.get(0).content());
        assertEquals("Review 2", result.get(1).content());
    }

    @Test
    void getCarReviewByUser_ShouldReturnUserCarReview() {
        // Arrange
        Long userId = 1L;
        Customer customer = new Customer();
        customer.setUserId(userId);
        customer.setFirstName("u1");
        customer.setLastName("smith");
        customer.setEmail("u1@gmail.com");
        CarReview carReview1 = new CarReview();
        carReview1.setCustomer(customer);
        carReview1.setContent("Review 1");

        CustomerResponseDto customerResponseDto=new CustomerResponseDto(1L,"u1","smith","u1@gmail.com");

        List<CarReview> carReviews = List.of(carReview1);
        CarReviewResponseDto responseDto1 = new CarReviewResponseDto(1L, "Review 1", 5, customerResponseDto, null, null, 2023, null);

        when(carReviewRepository.findCarReviewsByCustomer_UserId(Mockito.anyLong())).thenReturn(carReviews);
        when(carReviewMapper.carReviewToCarReviewResponseDto(Mockito.any(CarReview.class))).thenReturn(responseDto1);

        // Act
        List<CarReviewResponseDto> result = carReviewService.getCarReviewByUser(userId);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Review 1", result.get(0).content());
    }

    @Test
    void getCarReviewByMake_ShouldReturnMakeCarReview() {
        // Arrange
        String makeName = "Toyota";
        Make make = new Make();
        make.setMakeName(makeName);

        CarReview carReview = new CarReview();
        CarReviewResponseDto responseDto = new CarReviewResponseDto(1L, "Great car!", 5, null, null, null, 2023, null);

        when(makeRepository.findMakeByMakeName(Mockito.anyString())).thenReturn(Optional.of(make));
        when(carReviewRepository.findByMake(Mockito.any(Make.class))).thenReturn(List.of(carReview));
        when(carReviewMapper.carReviewToCarReviewResponseDto(Mockito.any(CarReview.class))).thenReturn(responseDto);

        // Act
        List<CarReviewResponseDto> result = carReviewService.getCarReviewByMake(makeName);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Great car!", result.get(0).content());
    }

    @Test
    void getCarReviewByMakeAndModel_ShouldReturnMakeModelCarReview() {
        // Arrange
        String makeName = "Toyota";
        String modelName = "Corolla";
        Make make = new Make();
        make.setMakeName(makeName);
        CarModel carModel = new CarModel();
        carModel.setModelName(modelName);

        CarReview carReview = new CarReview();
        CarReviewResponseDto responseDto = new CarReviewResponseDto(1L, "Great car!", 5, null, null, null, 2023, null);

        when(makeRepository.findMakeByMakeName(Mockito.anyString())).thenReturn(Optional.of(make));
        when(modelRepository.getModelByModelName(Mockito.anyString())).thenReturn(Optional.of(carModel));
        when(carReviewRepository.findByMakeAndCarModel(Mockito.any(Make.class),Mockito.any(CarModel.class))).thenReturn(List.of(carReview));
        when(carReviewMapper.carReviewToCarReviewResponseDto(Mockito.any(CarReview.class))).thenReturn(responseDto);

        // Act
        List<CarReviewResponseDto> result = carReviewService.getCarReviewByMakeAndModel(makeName, modelName);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Great car!", result.get(0).content());
    }

    @Test
    void getCarReviewByMakeAndModelYear_ShouldReturnMakeModelCarReview() {
        // Arrange
        String makeName = "Toyota";
        String modelName = "Corolla";
        Integer year = 2023;
        Make make = new Make();
        make.setMakeName(makeName);
        CarModel carModel = new CarModel();
        carModel.setModelName(modelName);

        CarReview carReview = new CarReview();
        CarReviewResponseDto responseDto = new CarReviewResponseDto(1L, "Great car!", 5, null, null, null, 2023, null);

        when(makeRepository.findMakeByMakeName(Mockito.anyString())).thenReturn(Optional.of(make));
        when(modelRepository.getModelByModelName(Mockito.anyString())).thenReturn(Optional.of(carModel));
        when(carReviewRepository.findByMakeAndCarModelAndYear(Mockito.any(Make.class),Mockito.any(CarModel.class),Mockito.anyInt())).thenReturn(List.of(carReview));
        when(carReviewMapper.carReviewToCarReviewResponseDto(Mockito.any(CarReview.class))).thenReturn(responseDto);

        // Act
        List<CarReviewResponseDto> result = carReviewService.getCarReviewByMakeModelYear(makeName, modelName,year);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Great car!", result.get(0).content());
        assertEquals(2023, result.get(0).year());
    }

    @Test
    void updateCarReview_ShouldReturnUpdatedReview() {
        // Arrange
        Long userId = 1L;
        Long reviewId = 2L;
        CarModelRequestDto carModelRequestDto = new CarModelRequestDto("Camry");
        MakeRequestDto makeRequestDto = new MakeRequestDto("Toyota");
        CustomerRequestDto customerRequestDto = new CustomerRequestDto("customer@example.com");

        CarReviewRequestDto requestDto = new CarReviewRequestDto(
                "Great car!", 5, makeRequestDto, carModelRequestDto, 2023, customerRequestDto
        );

        CarModel carModel = new CarModel();
        carModel.setModelName("Camry");

        Make make = new Make();
        make.setMakeName("Toyota");

        Customer customer = new Customer();
        customer.setEmail("customer@example.com");

        CarReview carReview = new CarReview();
        carReview.setReviewId(reviewId);
        carReview.setContent("Great car!");
        carReview.setStar(5);
        carReview.setMake(make);
        carReview.setCarModel(carModel);
        carReview.setCustomer(customer);

        CarReviewResponseDto responseDto = new CarReviewResponseDto(1L, "Great car!", 5, null, null, null, 2023, null);

        when(carReviewRepository.findCarReviewsByCustomer_UserIdAndReviewId(Mockito.anyLong(),Mockito.anyLong())).thenReturn(Optional.of(carReview));
        when(modelRepository.getModelByModelName(Mockito.anyString())).thenReturn(Optional.of(carModel));
        when(makeRepository.findMakeByMakeName(Mockito.anyString())).thenReturn(Optional.of(make));
        when(customerRepository.findCustomerByEmail(Mockito.anyString())).thenReturn(Optional.of(customer));
        when(carReviewRepository.save(Mockito.any(CarReview.class))).thenReturn(carReview);
        when(carReviewMapper.carReviewToCarReviewResponseDto(carReview)).thenReturn(responseDto);

        // Act
        Optional<CarReviewResponseDto> result = carReviewService.updateCarReview(userId,reviewId,requestDto);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Great car!", result.get().content());
        assertEquals(5, result.get().star());
        verify(carReviewRepository, times(1)).save(carReview);
    }

    @Test
    void updateCarReviewPartially_ShouldReturnUpdatedReview() {
        // Arrange
        Long userId = 1L;
        Long reviewId = 2L;
        CarModelRequestDto carModelRequestDto = new CarModelRequestDto("Camry");
        MakeRequestDto makeRequestDto = new MakeRequestDto("Toyota");
        CustomerRequestDto customerRequestDto = new CustomerRequestDto("customer@example.com");

        CarReviewRequestDto requestDto = new CarReviewRequestDto(
                "Great car!", 5, makeRequestDto, carModelRequestDto, 2023, customerRequestDto
        );

        CarModel carModel = new CarModel();
        carModel.setModelName("Camry");

        Make make = new Make();
        make.setMakeName("Toyota");

        Customer customer = new Customer();
        customer.setEmail("customer@example.com");

        CarReview carReview = new CarReview();
        carReview.setReviewId(reviewId);
        carReview.setContent("Great car!");
        carReview.setStar(5);
        carReview.setMake(make);
        carReview.setCarModel(carModel);
        carReview.setCustomer(customer);

        CarReviewResponseDto responseDto = new CarReviewResponseDto(1L, "Great car!", 5, null, null, null, 2023, null);

        when(carReviewRepository.findCarReviewsByCustomer_UserIdAndReviewId(Mockito.anyLong(),Mockito.anyLong())).thenReturn(Optional.of(carReview));
        when(modelRepository.getModelByModelName(Mockito.anyString())).thenReturn(Optional.of(carModel));
        when(makeRepository.findMakeByMakeName(Mockito.anyString())).thenReturn(Optional.of(make));
        when(customerRepository.findCustomerByEmail(Mockito.anyString())).thenReturn(Optional.of(customer));
        when(carReviewRepository.save(Mockito.any(CarReview.class))).thenReturn(carReview);
        when(carReviewMapper.carReviewToCarReviewResponseDto(carReview)).thenReturn(responseDto);

        // Act
        Optional<CarReviewResponseDto> result = carReviewService.updateCarReviewPartially(userId,reviewId,requestDto);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Great car!", result.get().content());
        assertEquals(5, result.get().star());
        verify(carReviewRepository, times(1)).save(carReview);
    }

    @Test
    void deleteCarReview_ShouldDeleteIfExists() {
        // Arrange
        Long userId = 1L;
        Long reviewId = 1L;
        CarReview carReview = new CarReview();

        when(carReviewRepository.findCarReviewsByCustomer_UserIdAndReviewId(userId, reviewId))
                .thenReturn(Optional.of(carReview));

        // Act
        carReviewService.deleteCarReview(userId, reviewId);

        // Assert
        verify(carReviewRepository, times(1)).deleteById(reviewId);
    }
}
