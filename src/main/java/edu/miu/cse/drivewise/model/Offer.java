package edu.miu.cse.drivewise.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;
    @Enumerated(EnumType.STRING)
    private OfferStatus offerStatus;
    private double offerPrice;
    private boolean cancel_flag=false;

    @CreationTimestamp
    private LocalDateTime crateDateTime;
    @UpdateTimestamp
    private LocalDateTime updateDateTime;
}
