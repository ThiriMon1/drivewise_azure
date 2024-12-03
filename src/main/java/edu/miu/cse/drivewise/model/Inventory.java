package edu.miu.cse.drivewise.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private Make make;
    @ManyToOne
    private CarModel model;
    private Integer year;
    private double currentPrice;
    private Integer mileage;
    @ManyToOne
    private City city;
    @ManyToOne
    private State state;
    @Enumerated(EnumType.STRING)
    private SellerType sellerType;
    private String color;
    private String transmission;
    private String VIN;
    private String engineType;
    private String drivetrain;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    private int mpgCity;
    private int mpgHighway;
    private String interiorMaterial;
    private int numberOfSeats;
    private String bodyType;
    @Enumerated(EnumType.STRING)
    private CarCondition carCondition;
    private String previousOwners;
    private String accidentHistory;
    private String warranty;
    @ElementCollection
    private List<String> features;
    private String trimLevel;
    @ManyToOne(cascade = CascadeType.ALL)
    private Dealer dealerInfo;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "inventory_price_history",
    joinColumns = {@JoinColumn(name = "inventory_id")},
    inverseJoinColumns = {@JoinColumn(name = "pricehistory_id")})
    private List<PriceHistory> priceHistory = new ArrayList<>();

    private LocalDate listingDate;
    @ElementCollection
    @CollectionTable(name = "car_photos", joinColumns = @JoinColumn(name = "car_id"))
    @Column(name = "photo_url")
    private List<String> photos;
    private double rating;
    @Enumerated(EnumType.STRING)
    private Status status=Status.AVAILABLE;
    private String inspectionDetails;
    private LocalDate saleDate;

    @CreationTimestamp
    private LocalDateTime createDateTime;
    @UpdateTimestamp
    private LocalDateTime updateDateTime;

}
