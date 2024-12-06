package edu.miu.cse.drivewise.controller;

import edu.miu.cse.drivewise.dto.request.OfferRequestDto;
import edu.miu.cse.drivewise.dto.response.OfferResponseDto;
import edu.miu.cse.drivewise.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/offers")
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;

    // Register an offer
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PostMapping
    public ResponseEntity<OfferResponseDto> createOfferByUser(@RequestParam(name = "userId") Long userId,
                                                              @RequestBody OfferRequestDto offerRequestDto){

        Optional<OfferResponseDto> offerResponseDto=offerService.saveOfferByUser(userId,offerRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(offerResponseDto.get());
    }

    // Cancel an offer
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PatchMapping("/{offerId}/user-cancel")
    public ResponseEntity<String> cancelOfferByUser(@RequestParam(name = "userId") Long userId,
                                                  @PathVariable Long offerId){
        String result=offerService.cancelOfferByUser(userId,offerId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // Get all offers
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<OfferResponseDto>> getAllOffer(){
        List<OfferResponseDto> offerResponseDtos=offerService.getAllOffers();
        return ResponseEntity.status(HttpStatus.OK).body(offerResponseDtos);
    }

    // Get offers by specific user
    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER')")
    @GetMapping("/user-offer")
    public ResponseEntity<List<OfferResponseDto>> getOffersByUser(@RequestParam(name = "userId") Long userId){
        List<OfferResponseDto> offerResponseDtos=offerService.getOffersByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(offerResponseDtos);
    }

    // Get offers by filter
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/offers-filter")
    public ResponseEntity<List<OfferResponseDto>> getOfferListByFilter(
            @RequestParam(name = "userId",required = false) Long userId,
            @RequestParam(name = "inventoryId",required = false) Long inventoryId) {
        if (userId != null && inventoryId == null) {
            List<OfferResponseDto> offerResponseDtos = offerService.getOffersByUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body(offerResponseDtos);
        } else if (inventoryId != null && userId == null) {
            List<OfferResponseDto> offerResponseDtos = offerService.getOffersByInventoryId(inventoryId);
            return ResponseEntity.status(HttpStatus.OK).body(offerResponseDtos);
        } else if (userId != null && inventoryId != null) {
            List<OfferResponseDto> offerResponseDtos = offerService.getOffersByUserAndInventoryId(userId, inventoryId);
            return ResponseEntity.status(HttpStatus.OK).body(offerResponseDtos);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    // Accept an offer
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{offerId}/accept")
    public ResponseEntity<OfferResponseDto> acceptOffer(@PathVariable Long offerId){
        Optional<OfferResponseDto> offerResponseDto=offerService.approveOffer(offerId);
        return ResponseEntity.status(HttpStatus.OK).body(offerResponseDto.get());
    }

    // Reject an offer
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{offerId}/reject")
    public ResponseEntity<OfferResponseDto> rejectOffer(@PathVariable Long offerId){
        Optional<OfferResponseDto> offerResponseDto=offerService.rejectOffer(offerId);
        return ResponseEntity.status(HttpStatus.OK).body(offerResponseDto.get());
    }

    // Finalize an offer
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{offerId}/finalize")
    public ResponseEntity<OfferResponseDto> finalizeSale(@PathVariable Long offerId){
        Optional<OfferResponseDto> offerResponseDto=offerService.finalizeSale(offerId);
        return ResponseEntity.status(HttpStatus.OK).body(offerResponseDto.get());
    }

    // Cancel a pending sale offer
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{offerId}/cancel")
    public ResponseEntity<OfferResponseDto> cancelPendingSale(@PathVariable Long offerId){
        Optional<OfferResponseDto> offerResponseDto=offerService.cancelPendingSale(offerId);
        return ResponseEntity.status(HttpStatus.OK).body(offerResponseDto.get());
    }

    // Get an offer by offer id
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/{offerId}")
    public ResponseEntity<OfferResponseDto> getOfferByOfferId(@PathVariable Long offerId){
        Optional<OfferResponseDto> offerResponseDto=offerService.getOfferById(offerId);
        return ResponseEntity.status(HttpStatus.OK).body(offerResponseDto.get());
    }

}
