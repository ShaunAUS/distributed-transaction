package com.example.product.infrastructure;

import com.example.product.domain.ProductReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReservationRepository extends JpaRepository<ProductReservation, Long> {
}
