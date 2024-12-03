package edu.miu.cse.drivewise.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Dealer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String dealerName;
    private String phone;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "dealer_address",
    joinColumns = {@JoinColumn(name = "dealer_id")},
    inverseJoinColumns = {@JoinColumn(name = "address_id")})
    private Address address;
    private String rating;
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<Inventory> inventories;

    @Override
    public String toString() {
        return "Dealer{" +
                "name='" + dealerName + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address +
                ", rating='" + rating + '\'' +
                '}';
    }
}
