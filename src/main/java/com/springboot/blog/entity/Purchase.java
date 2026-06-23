package com.springboot.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchases")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", nullable = false)
    private CoursePackage coursePackage;

    @Column(nullable = false, unique = true)
    private String orderId;

    @Column(nullable = false)
    private String paymentId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String paymentStatus;

    private String referralCode;

    @Column(nullable = false)
    private LocalDateTime purchaseDate = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean commissionProcessed = false;
}
