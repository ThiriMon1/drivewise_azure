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
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String stateName;
    @OneToMany(mappedBy = "state")
    private List<City> cities;

    @Override
    public String toString() {
        return "State{" +
                "stateName='" + stateName + '\'' +
                '}';
    }
}
