package com.example.product.application;

import com.example.product.infrastructure.ProductRepository;
import com.example.product.infrastructure.ProductReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductReservationRepository productReservationRepository;

    public ProductService(ProductRepository productRepository, ProductReservationRepository productReservationRepository) {
        this.productRepository = productRepository;
        this.productReservationRepository = productReservationRepository;
    }

    public void tryReserve(){


    }

}
