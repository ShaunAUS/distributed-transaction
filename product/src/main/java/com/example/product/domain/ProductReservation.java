package com.example.product.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "product_reservations")
public class ProductReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String requestID;

    private Long productId;

    private Long reservedQuantity;

    private Long reservedPrice;

    @Enumerated(EnumType.STRING)
    private ProductReservationStatus status;

    public ProductReservation() {
    }

    public ProductReservation(String requestID, Long productId, Long reservedQuantity, Long reservedPrice) {
        this.requestID = requestID;
        this.productId = productId;
        this.reservedQuantity = reservedQuantity;
        this.reservedPrice = reservedPrice;
        status = ProductReservationStatus.RESERVED;
    }

    public enum ProductReservationStatus {
        RESERVED,
        CONFIRMED,
        CANCELED,
    }


}
