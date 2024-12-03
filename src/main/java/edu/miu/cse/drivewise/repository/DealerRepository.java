package edu.miu.cse.drivewise.repository;

import edu.miu.cse.drivewise.model.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealerRepository extends JpaRepository<Dealer, Integer> {
}
