package com.rapidshine.carwash.user_service.repository;

import com.rapidshine.carwash.user_service.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car,Long> {
}
