package edu.miu.cse.drivewise.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Make {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long makeId;
    @Column(unique=true)
    private String makeName;
    @OneToMany(mappedBy = "make", cascade = CascadeType.PERSIST)
    private List<CarModel> modelList;
    @OneToMany(mappedBy = "make", cascade = CascadeType.PERSIST)
    private List<CarReview> reviewList;

    @Override
    public String toString() {
        return "Make{" +
                "makeName='" + makeName + '\'' +
                ", modelList=" + modelList +
                '}';
    }
}
