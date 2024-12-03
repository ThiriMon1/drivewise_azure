package edu.miu.cse.drivewise.repository;

import edu.miu.cse.drivewise.model.Offer;
import edu.miu.cse.drivewise.model.OfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findOffersByCustomer_UserId(Long userId);
    List<Offer> findOffersByInventory_Id(Long inventoryId);
  //  @Query("select o from Offer o where o.offerStatus=:offerStatus and o.inventory.id=:inventory")
    List<Offer> findOffersByOfferStatusAndInventory_Id(OfferStatus offerStatus, Long inventory);
    List<Offer> findOffersByCustomer_UserIdAndInventory_Id(Long customerId, Long inventoryId);
}
