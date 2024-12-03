package edu.miu.cse.drivewise.repository;

import edu.miu.cse.drivewise.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Integer> {
    Optional<City> findCityByCityName(String name);
}
