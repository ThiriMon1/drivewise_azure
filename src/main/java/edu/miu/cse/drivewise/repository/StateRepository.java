package edu.miu.cse.drivewise.repository;

import edu.miu.cse.drivewise.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StateRepository extends JpaRepository<State, Integer> {
    Optional<State> findStateByStateName(String statename);
}
