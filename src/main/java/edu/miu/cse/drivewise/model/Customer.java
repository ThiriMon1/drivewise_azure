package edu.miu.cse.drivewise.model;

import edu.miu.cse.drivewise.user.Role;
import edu.miu.cse.drivewise.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Customer extends User {

    private String phone;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Offer> offers;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CarReview> carReviews;

    @ManyToMany
    @JoinTable(
            name="favorite_inventories",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "inventory_id")
    )
    private List<Inventory> favoriteInventories = new ArrayList<>();

    public Customer(String firstName, String lastName, String email, String password, Role role) {
        super(firstName, lastName, email, password, role);
    }


}
