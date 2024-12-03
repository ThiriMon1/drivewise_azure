package edu.miu.cse.drivewise.service;

import edu.miu.cse.drivewise.dto.request.OfferRequestDto;
import edu.miu.cse.drivewise.dto.response.OfferResponseDto;

import java.util.List;
import java.util.Optional;

public interface OfferService {
    Optional<OfferResponseDto> saveOfferByUser(Long userId, OfferRequestDto offerRequestDto);
    String cancelOfferByUser(Long userId, Long offerId);
    List<OfferResponseDto> getOffersByUser(Long userId);
    List<OfferResponseDto> getOffersByInventoryId(Long inventoryId);
    List<OfferResponseDto> getOffersByUserAndInventoryId(Long userId, Long inventoryId);
    List<OfferResponseDto> getAllOffers();
    Optional<OfferResponseDto> approveOffer(Long offerId);
    Optional<OfferResponseDto> rejectOffer(Long offerId);
    Optional<OfferResponseDto> getOfferById(Long offerId);
    Optional<OfferResponseDto> finalizeSale(Long offerId);
    Optional<OfferResponseDto> cancelPendingSale(Long offerId);


}
