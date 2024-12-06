package edu.miu.cse.drivewise.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long modelId;
    @Column(unique = true, nullable = false)
    private String modelName;
    @OneToMany(mappedBy = "model")
    private List<Inventory> inventories;
    @ManyToOne
    @JoinColumn(name="make_id")
    private Make make;
    @OneToMany(mappedBy = "carModel", cascade = CascadeType.PERSIST)
    private List<CarReview> carReviews;

    @Override
    public String toString() {
        return "CarModel{" +
                "modelName='" + modelName + '\'' +
                '}';
    }
}
