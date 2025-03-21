package com.rapidshine.carwash.user_service.repository;

import com.rapidshine.carwash.user_service.model.Washer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasherRepository extends JpaRepository<Washer,Long> {
}
