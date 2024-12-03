package edu.miu.cse.drivewise.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@Data
public class CarReview {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long reviewId;
    private String content;
    private int star;
    @ManyToOne
    @JoinColumn(name ="make_id")
    private Make make;
    @ManyToOne
    @JoinColumn(name = "carModel_id")
    private CarModel carModel;
    private int year;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
