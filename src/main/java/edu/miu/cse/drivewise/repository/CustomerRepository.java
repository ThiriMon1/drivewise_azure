package edu.miu.cse.drivewise.repository;

import edu.miu.cse.drivewise.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c where c.email=:email")
    Optional<Customer> findCustomerByEmail(String email);
}
