package com.springboot.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "commissions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "affiliate_id", nullable = false)
    private User affiliate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Double commissionRate;

    @Column(nullable = false)
    private String status = "PENDING";

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
