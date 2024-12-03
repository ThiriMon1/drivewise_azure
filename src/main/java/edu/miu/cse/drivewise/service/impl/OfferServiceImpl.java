package edu.miu.cse.drivewise.service.impl;

import edu.miu.cse.drivewise.dto.request.OfferRequestDto;
import edu.miu.cse.drivewise.dto.response.OfferCustomerResponseDto;
import edu.miu.cse.drivewise.dto.response.OfferResponseDto;
import edu.miu.cse.drivewise.exception.resourcenotfound.ResourceNotFoundException;
import edu.miu.cse.drivewise.exception.offer.*;
import edu.miu.cse.drivewise.model.*;
import edu.miu.cse.drivewise.repository.CustomerRepository;
import edu.miu.cse.drivewise.repository.InventoryRepository;
import edu.miu.cse.drivewise.repository.OfferRepository;
import edu.miu.cse.drivewise.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final CustomerRepository customerRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    public Optional<OfferResponseDto> saveOfferByUser(Long userId, OfferRequestDto offerRequestDto) {
        //Check customer and inventory by id if they are existed
        Customer customer = getCustomer(userId);
        Inventory inventory = getInventory(offerRequestDto.inventoryId());

        //Validate car is available or throw an exception if not available
        if (inventory.getStatus().equals(Status.AVAILABLE)) {
            //Creates a new offer with the provided details, associates it with the customer and inventory,
            //sets its status to PENDING, and saves it in the repository.
            Offer offer = new Offer();
            offer.setCustomer(customer);
            offer.setInventory(inventory);
            offer.setOfferPrice(offerRequestDto.offerPrice());
            offer.setOfferStatus(OfferStatus.PENDING);
            Offer savedOffer = offerRepository.save(offer);

            customer.setPhone(offerRequestDto.customerPhone());
            customerRepository.save(customer);

            return Optional.of(mapToOfferResponseDto(savedOffer));
        } else {
            throw new InventoryIsNotAvailableException("inventory is not available.");
        }

    }

    @Override
    public String cancelOfferByUser(Long userId, Long offerId) {
        //Check customer and offer by id if they are existed
        getCustomer(userId);
        Offer offer = getOffer(offerId);
        //Validate offer has not cancelled and offer status still pending
        if (!offer.isCancel_flag()) {
            if (offer.getOfferStatus().equals(OfferStatus.PENDING)) {
                //Cancel flag true and save it
                offer.setCancel_flag(Boolean.TRUE);
                offerRepository.save(offer);
                return "Offer is successfully cancelled.";
            } else {
                throw new OfferCannotCancelException("cannot cancel. Already accepted.");
            }
        } else {
            throw new OfferAlreadyCancelledException("offer is already cancelled");
        }
    }

    @Override
    public List<OfferResponseDto> getOffersByUser(Long userId) {
        //Check customer by id if it is existed
        Customer customer = getCustomer(userId);
        List<OfferResponseDto> offerResponseDtos = new ArrayList<>();
        if (customer != null) {
            //Get offer list by specific user
            List<Offer> offers = offerRepository.findOffersByCustomer_UserId(userId);
            if (offers != null) {
                offerResponseDtos = offers.stream()
                        .map(this::mapToOfferResponseDto).toList();
                return offerResponseDtos;
            } else {
                throw new ResourceNotFoundException("There is no offer!");
            }
        } else {
            throw new ResourceNotFoundException(userId + " is not found");
        }
    }

    @Override
    public List<OfferResponseDto> getOffersByInventoryId(Long inventoryId) {
        //Check inventory by Id if it is existed
        Inventory inventory = getInventory(inventoryId);
        if (inventory != null) {
            //Get offer list by specific inventory
            List<Offer> offers = offerRepository.findOffersByInventory_Id(inventoryId);
            return offers.stream().map(this::mapToOfferResponseDto).toList();
        } else {
            throw new ResourceNotFoundException(inventoryId + " is not found");
        }
    }

    @Override
    public List<OfferResponseDto> getOffersByUserAndInventoryId(Long userId, Long inventoryId) {
        getCustomer(userId);
        getInventory(inventoryId);
        //Get offer list by specific user and inventory
        List<Offer> offers = offerRepository.findOffersByCustomer_UserIdAndInventory_Id(userId,inventoryId);
        return offers.stream().map(this::mapToOfferResponseDto).toList();

    }

    @Override
    public List<OfferResponseDto> getAllOffers() {
        //Get all offers
        List<Offer> offers = offerRepository.findAll();
        return offers.stream().map(this::mapToOfferResponseDto).toList();
    }

    @Override
    public Optional<OfferResponseDto> approveOffer(Long offerId) {
        //Check offer by id if it is existed
        Offer offer = getOffer(offerId);
        if (offer != null && !offer.isCancel_flag()) {
            if (offer.getInventory().getStatus().equals(Status.AVAILABLE)) {
                //update offer status and car status
                offer.setOfferStatus(OfferStatus.ACCEPTED);
                offer.getInventory().setStatus(Status.PENDING_SALE);

                //save updated car status in inventory repo
                inventoryRepository.save(offer.getInventory());
                //save updated offer status in offer repo
                Offer updatedOffer = offerRepository.save(offer);
                return Optional.of(mapToOfferResponseDto(updatedOffer));
            } else {
                throw new InventoryIsNotAvailableException("Inventory must be available to accept offer");
            }
        } else {
            throw new OfferAlreadyCancelledException("offer has been cancelled");
        }
    }

    @Override
    public Optional<OfferResponseDto> rejectOffer(Long offerId) {
        Offer offer = getOffer(offerId);
        if (offer != null) {
            //check offer status before reject
            if (offer.getOfferStatus().equals(OfferStatus.ACCEPTED)) {
                //update offer status and car status
                offer.setOfferStatus(OfferStatus.REJECTED);
                offer.getInventory().setStatus(Status.AVAILABLE);

                //save updated car status in inventory repo
                inventoryRepository.save(offer.getInventory());
                //save updated offer status in offer repo
                Offer updatedOffer = offerRepository.save(offer);
                return Optional.of(mapToOfferResponseDto(updatedOffer));
            } else {
                throw new WrongOfferStatusException("Offer status must be 'ACCEPTED' to reject");
            }

        } else {
            throw new ResourceNotFoundException("offer is not found or cancelled");
        }
    }

    @Override
    public Optional<OfferResponseDto> finalizeSale(Long offerId) {
        Offer offer = getOffer(offerId);
        if (offer != null && offer.getOfferStatus().equals(OfferStatus.ACCEPTED)
                && offer.getInventory().getStatus().equals(Status.PENDING_SALE)) {
            //update car status: SOLD in inventory
            offer.getInventory().setStatus(Status.SOLD);
            offer.getInventory().setSaleDate(LocalDate.now());
            inventoryRepository.save(offer.getInventory());

            //reject all pending offers
            List<Offer> pendingOffers = new ArrayList<>();
            pendingOffers = offerRepository.findOffersByOfferStatusAndInventory_Id(OfferStatus.PENDING, offer.getInventory().getId());
            if (pendingOffers != null) {
                for (Offer pOffer : pendingOffers) {
                    pOffer.setOfferStatus(OfferStatus.REJECTED);
                    offerRepository.save(pOffer);
                }
            }

            return Optional.of(mapToOfferResponseDto(offer));
        } else {
            throw new OfferCannotFinalizeSaleException("To finalize the offer, the offer status must be 'ACCEPTED' and the inventory status must be 'PENDING_SALE'.");
        }
    }

    @Override
    public Optional<OfferResponseDto> cancelPendingSale(Long offerId) {
        Offer offer = getOffer(offerId);
        if (offer != null && offer.getOfferStatus().equals(OfferStatus.ACCEPTED)
                && offer.getInventory().getStatus().equals(Status.PENDING_SALE)) {
            //update offer status and car status
            offer.setOfferStatus(OfferStatus.REJECTED);
            offer.getInventory().setStatus(Status.AVAILABLE);

            //save updated car status in inventory repo
            inventoryRepository.save(offer.getInventory());
            //save updated offer status in offer repo
            Offer updatedOffer = offerRepository.save(offer);
            return Optional.of(mapToOfferResponseDto(updatedOffer));
        } else {
            throw new OfferCannotCancelException("To cancel the offer, the offer status must be 'ACCEPTED' and the inventory status must be 'PENDING_SALE'. ");
        }
    }

    @Override
    public Optional<OfferResponseDto> getOfferById(Long offerId) {
        Offer offer = getOffer(offerId);
        return Optional.of(mapToOfferResponseDto(offer));

    }


    private OfferResponseDto mapToOfferResponseDto(Offer offer) {
        OfferCustomerResponseDto offerCustomerResponseDto = new OfferCustomerResponseDto(offer.getCustomer().getUserId(), offer.getCustomer().getFirstName(), offer.getCustomer().getLastName(),
                offer.getCustomer().getEmail(),offer.getCustomer().getPhone());
        return new OfferResponseDto(offer.getId(), offerCustomerResponseDto, offer.getInventory().getId(), offer.getInventory().getMake().getMakeName(),
                offer.getInventory().getModel().getModelName(), offer.getInventory().getYear(), offer.getInventory().getMileage(),
                offer.getInventory().getCity().getCityName(), offer.getInventory().getState().getStateName(), offer.getInventory().getCurrentPrice(),
                offer.getInventory().getStatus(), offer.getInventory().getPhotos(), offer.getOfferStatus(), offer.getOfferPrice(), offer.isCancel_flag());

    }

    private Customer getCustomer(Long userId) {
        return customerRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(userId + " is not found"));
    }

    private Inventory getInventory(Long inventoryId) {
        return inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new ResourceNotFoundException(inventoryId + " is not found"));
    }

    private Offer getOffer(Long offerId) {
        return offerRepository.findById(offerId)
                .orElseThrow(() -> new ResourceNotFoundException(offerId + " is not found"));
    }
}
