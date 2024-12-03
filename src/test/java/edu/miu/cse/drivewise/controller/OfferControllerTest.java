package edu.miu.cse.drivewise.controller;

import edu.miu.cse.drivewise.dto.request.OfferRequestDto;
import edu.miu.cse.drivewise.dto.response.CustomerResponseDto;
import edu.miu.cse.drivewise.dto.response.OfferCustomerResponseDto;
import edu.miu.cse.drivewise.dto.response.OfferResponseDto;
import edu.miu.cse.drivewise.model.OfferStatus;
import edu.miu.cse.drivewise.model.Status;
import edu.miu.cse.drivewise.service.OfferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class OfferControllerTest {

    @Mock
    OfferService offerService;

    @InjectMocks
    OfferController offerController;

    OfferResponseDto mockResponse;

    @BeforeEach
    void setUp() {
        mockResponse = new OfferResponseDto(
                1L,
                new OfferCustomerResponseDto(1L, "u1", "Smith", "u1@gmail.com","032434435"),
                1L,
                "Toyota",
                "Corolla",
                2019,
                100000,
                "Chicago",
                "Illinois",
                18000,
                Status.AVAILABLE,
                List.of("picture1.png", "picture2.png"),
                OfferStatus.PENDING,
                18000,
                Boolean.FALSE
        );
    }

    @Test
    void createOfferByUser_validInput_returnOffer() {
        Mockito.when(offerService.saveOfferByUser(anyLong(), any(OfferRequestDto.class)))
                .thenReturn(Optional.of(mockResponse));

        ResponseEntity<OfferResponseDto> response = offerController.createOfferByUser(
                1L, new OfferRequestDto(1L, 30000,"32435435435"));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void cancelOfferByUser() {
        Mockito.when(offerService.cancelOfferByUser(anyLong(), anyLong()))
                .thenReturn("Offer canceled successfully");

        ResponseEntity<String> response = offerController.cancelOfferByUser(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Offer canceled successfully", response.getBody());
    }

    @Test
    void getAllOffer() {
        Mockito.when(offerService.getAllOffers()).thenReturn(List.of(mockResponse));

        ResponseEntity<List<OfferResponseDto>> response = offerController.getAllOffer();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(mockResponse, response.getBody().get(0));
    }

    @Test
    void getOffersByUser() {
        Mockito.when(offerService.getOffersByUser(anyLong())).thenReturn(List.of(mockResponse));

        ResponseEntity<List<OfferResponseDto>> response = offerController.getOffersByUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(mockResponse, response.getBody().get(0));
    }

    @Test
    void getOfferListByFilter() {
        Mockito.when(offerService.getOffersByUserAndInventoryId(anyLong(), anyLong()))
                .thenReturn(List.of(mockResponse));

        ResponseEntity<List<OfferResponseDto>> response = offerController.getOfferListByFilter(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(mockResponse, response.getBody().get(0));
    }

    @Test
    void acceptOffer() {
        Mockito.when(offerService.approveOffer(anyLong())).thenReturn(Optional.of(mockResponse));

        ResponseEntity<OfferResponseDto> response = offerController.acceptOffer(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void rejectOffer() {
        Mockito.when(offerService.rejectOffer(anyLong())).thenReturn(Optional.of(mockResponse));

        ResponseEntity<OfferResponseDto> response = offerController.rejectOffer(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void finalizeSale() {
        Mockito.when(offerService.finalizeSale(anyLong())).thenReturn(Optional.of(mockResponse));

        ResponseEntity<OfferResponseDto> response = offerController.finalizeSale(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void cancelPendingSale() {
        Mockito.when(offerService.cancelPendingSale(anyLong())).thenReturn(Optional.of(mockResponse));

        ResponseEntity<OfferResponseDto> response = offerController.cancelPendingSale(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void getOfferByOfferId() {
        Mockito.when(offerService.getOfferById(anyLong())).thenReturn(Optional.of(mockResponse));

        ResponseEntity<OfferResponseDto> response = offerController.getOfferByOfferId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockResponse, response.getBody());
    }
}
