package edu.miu.cse.drivewise.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int addressId;
    private String number;
    private String street;
    private String zipCode;
    @ManyToOne
    private City city;
    @ManyToOne
    private State state;

    @Override
    public String toString() {
        return "Address{" +
                "number='" + number + '\'' +
                ", street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city=" + city.getCityName() +'\''+
                ", state=" + state.getStateName() +
                '}';
    }
}
