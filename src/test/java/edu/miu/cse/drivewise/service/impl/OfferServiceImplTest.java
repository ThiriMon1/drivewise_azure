package edu.miu.cse.drivewise.service.impl;

import edu.miu.cse.drivewise.dto.request.OfferRequestDto;
import edu.miu.cse.drivewise.dto.response.OfferResponseDto;
import edu.miu.cse.drivewise.exception.offer.OfferAlreadyCancelledException;
import edu.miu.cse.drivewise.exception.offer.OfferCannotFinalizeSaleException;
import edu.miu.cse.drivewise.exception.resourcenotfound.ResourceNotFoundException;
import edu.miu.cse.drivewise.exception.offer.InventoryIsNotAvailableException;
import edu.miu.cse.drivewise.model.*;
import edu.miu.cse.drivewise.repository.CustomerRepository;
import edu.miu.cse.drivewise.repository.InventoryRepository;
import edu.miu.cse.drivewise.repository.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
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
class OfferServiceImplTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private OfferServiceImpl offerService;

    private Customer mockCustomer;
    private Inventory mockInventory;
    private Offer mockOffer;
    private OfferRequestDto mockOfferRequestDto;

    @BeforeEach
    void setup() {
        mockCustomer = new Customer();
        mockCustomer.setUserId(1L);

        mockInventory = new Inventory();
        mockInventory.setId(1L);
        Make make = new Make();
        make.setMakeName("Toyota");
        mockInventory.setMake(make);
        CarModel carModel = new CarModel();
        carModel.setModelName("Corolla");
        mockInventory.setModel(carModel);
        mockInventory.setYear(2023);
        mockInventory.setMileage(100000);
        City city = new City();
        city.setCityName("Chicago");
        mockInventory.setCity(city);
        State state= new State();
        state.setStateName("IL");
        mockInventory.setState(state);
        mockInventory.setCurrentPrice(19000);
        mockInventory.setStatus(Status.AVAILABLE);
        mockInventory.setPhotos(List.of("picture.png","picture1.png"));

        mockOffer = new Offer();
        mockOffer.setId(1L);
        mockOffer.setCustomer(mockCustomer);
        mockOffer.setInventory(mockInventory);
        mockOffer.setOfferStatus(OfferStatus.PENDING);
        mockOffer.setOfferPrice(19000);
        mockOffer.setCancel_flag(false);

        mockOfferRequestDto = new OfferRequestDto(1L, 20000,"16244354545");
    }

    @Test
    void saveOfferByUser_WhenInventoryIsAvailable_ShouldSaveOffer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(mockCustomer));
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(mockInventory));
        when(offerRepository.save(any(Offer.class))).thenReturn(mockOffer);
        when(customerRepository.save(any(Customer.class))).thenReturn(mockCustomer);

        Optional<OfferResponseDto> result = offerService.saveOfferByUser(1L, mockOfferRequestDto);

        assertTrue(result.isPresent());
        assertEquals(OfferStatus.PENDING, result.get().offerStatus());
        verify(offerRepository).save(any(Offer.class));
    }

    @Test
    void saveOfferByUser_WhenInventoryIsNotAvailable_ShouldThrowException() {
        mockInventory.setStatus(Status.SOLD);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(mockCustomer));
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(mockInventory));

        assertThrows(InventoryIsNotAvailableException.class, () ->
                offerService.saveOfferByUser(1L, mockOfferRequestDto));
    }

    @Test
    void cancelOfferByUser_WhenOfferIsPending_ShouldCancelOffer() {
        mockOffer.setCancel_flag(false);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(mockCustomer));
        when(offerRepository.findById(1L)).thenReturn(Optional.of(mockOffer));
        when(offerRepository.save(mockOffer)).thenReturn(mockOffer);

        String result = offerService.cancelOfferByUser(1L, 1L);

        assertEquals("Offer is successfully cancelled.", result);
        assertTrue(mockOffer.isCancel_flag());
        verify(offerRepository).save(mockOffer);
    }

    @Test
    void getOffersByUser_WhenCustomerExists_ShouldReturnOffers() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(mockCustomer));
        when(offerRepository.findOffersByCustomer_UserId(1L)).thenReturn(List.of(mockOffer));

        List<OfferResponseDto> result = offerService.getOffersByUser(1L);

        assertEquals(1, result.size());
        verify(offerRepository).findOffersByCustomer_UserId(1L);
    }

    @Test
    void getOffersByUser_WhenCustomerDoesNotExist_ShouldThrowException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> offerService.getOffersByUser(1L));
    }

    @Test
    void getOffersByInventoryId_WhenOffersExist_ShouldReturnOffers() {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(mockInventory));
        when(offerRepository.findOffersByInventory_Id(1L)).thenReturn(List.of(mockOffer));

        List<OfferResponseDto> result = offerService.getOffersByInventoryId(1L);

        assertEquals(1, result.size());
        verify(offerRepository).findOffersByInventory_Id(1L);
    }

    @Test
    void getOffersByUserAndInventoryId_WhenOffersExist_ShouldReturnOffers() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(mockCustomer));
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(mockInventory));
        when(offerRepository.findOffersByCustomer_UserIdAndInventory_Id(1L,1L)).thenReturn(List.of(mockOffer));

        List<OfferResponseDto> result = offerService.getOffersByUserAndInventoryId(1L,1L);

        assertEquals(1, result.size());
        verify(offerRepository).findOffersByCustomer_UserIdAndInventory_Id(1L,1L);
    }

    @Test
    void getAllOffers_returnOffers() {
        when(offerRepository.findAll()).thenReturn(List.of(mockOffer));

        List<OfferResponseDto> result = offerService.getAllOffers();
        assertEquals(1, result.size());
        verify(offerRepository).findAll();
    }


    @Test
    void approveOffer_WhenOfferAndInventoryAreValid_ShouldApproveOffer() {
        when(offerRepository.findById(1L)).thenReturn(Optional.of(mockOffer));
        when(inventoryRepository.save(mockInventory)).thenReturn(mockInventory);
        when(offerRepository.save(mockOffer)).thenReturn(mockOffer);

        Optional<OfferResponseDto> result = offerService.approveOffer(1L);

        assertTrue(result.isPresent());
        assertEquals(OfferStatus.ACCEPTED, result.get().offerStatus());
        assertEquals(Status.PENDING_SALE, mockInventory.getStatus());
        verify(offerRepository).save(mockOffer);
        verify(inventoryRepository).save(mockInventory);
    }

    @Test
    void approveOffer_WhenOfferIsCancelled_ShouldThrowException() {
        mockOffer.setCancel_flag(true);
        when(offerRepository.findById(1L)).thenReturn(Optional.of(mockOffer));

        assertThrows(OfferAlreadyCancelledException.class, () -> offerService.approveOffer(1L));
    }

    @Test
    void rejectOffer_ShouldRejectOffer() {
        mockOffer.setOfferStatus(OfferStatus.ACCEPTED);
        when(offerRepository.findById(1L)).thenReturn(Optional.of(mockOffer));

        when(inventoryRepository.save(mockInventory)).thenReturn(mockInventory);
        when(offerRepository.save(mockOffer)).thenReturn(mockOffer);

        Optional<OfferResponseDto> result = offerService.rejectOffer(1L);

        assertTrue(result.isPresent());
        assertEquals(OfferStatus.REJECTED, result.get().offerStatus());
        assertEquals(Status.AVAILABLE, mockInventory.getStatus());
        verify(offerRepository).save(mockOffer);
        verify(inventoryRepository).save(mockInventory);
    }

    @Test
    void finalizeSale_Success() {
        // Arrange
        mockInventory.setStatus(Status.PENDING_SALE);
        mockOffer.setOfferStatus(OfferStatus.ACCEPTED);
        mockOffer.setInventory(mockInventory);

        Offer pendingOffer = new Offer();
        pendingOffer.setOfferStatus(OfferStatus.PENDING);

        when(offerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockOffer));
        when(offerRepository.findOffersByOfferStatusAndInventory_Id(OfferStatus.PENDING, mockInventory.getId()))
                .thenReturn(List.of(pendingOffer));

        // Act
        Optional<OfferResponseDto> result = offerService.finalizeSale(mockOffer.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(Status.SOLD, mockInventory.getStatus());
        assertEquals(OfferStatus.ACCEPTED, result.get().offerStatus());
        assertNotNull(mockInventory.getSaleDate());
        verify(inventoryRepository).save(mockInventory);

    }

    @Test
    void cancelPendingSale_Success() {
        mockInventory.setStatus(Status.PENDING_SALE);
        mockOffer.setOfferStatus(OfferStatus.ACCEPTED);
        mockOffer.setInventory(mockInventory);

        when(offerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockOffer));
        when(offerRepository.save(Mockito.any(Offer.class))).thenReturn(mockOffer);

        Optional<OfferResponseDto> result = offerService.cancelPendingSale(1L);
        assertTrue(result.isPresent());
        assertEquals(OfferStatus.REJECTED, result.get().offerStatus());
        assertEquals(Status.AVAILABLE, mockInventory.getStatus());
        verify(inventoryRepository).save(mockInventory);
        verify(offerRepository).save(mockOffer);
    }

    @Test
    void getOfferById_returnOffer() {
        when(offerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockOffer));

        Optional<OfferResponseDto> result = offerService.getOfferById(1L);
        assertTrue(result.isPresent());
        verify(offerRepository).findById(1L);

    }

}
