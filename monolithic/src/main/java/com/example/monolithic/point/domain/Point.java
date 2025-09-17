package com.example.monolithic.point.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "points")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long amount;

    public Point() {
    }

    public Point(Long userId, Long amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public void reduce(Long amount) {
        if (this.amount < amount) {
            throw new RuntimeException("포인트가 부족합니다.");
        }
        this.amount = this.amount - amount;
    }
}
