package edu.miu.cse.drivewise.repository;

import edu.miu.cse.drivewise.model.CarCondition;
import edu.miu.cse.drivewise.model.Inventory;
import edu.miu.cse.drivewise.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findInventoryByStatus(Status status);

    List<Inventory> findByCarCondition(CarCondition condition);
}
