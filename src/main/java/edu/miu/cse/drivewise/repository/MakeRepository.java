package edu.miu.cse.drivewise.repository;

import edu.miu.cse.drivewise.model.Make;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MakeRepository extends JpaRepository<Make, Long> {
    Optional<Make> findMakeByMakeName(String name);

}
