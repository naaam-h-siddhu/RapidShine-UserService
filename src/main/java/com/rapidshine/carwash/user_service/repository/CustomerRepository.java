package com.rapidshine.carwash.user_service.repository;

import com.rapidshine.carwash.user_service.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
